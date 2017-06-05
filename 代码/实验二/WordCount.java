package cn.itcast.hadoop.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class WordCount {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		conf.setInt("mapreduce.client.submit.file.replication", "20");
		Job job = Job.getInstance(conf);
		
		//notice
		job.setJarByClass(WordCount.class);
		
		//set mapper`s property
		job.setMapperClass(WCMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		FileInputFormat.setInputPaths(job, new Path("/root/words.txt"));
		
		//set reducer`s property
		job.setReducerClass(WCReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		FileOutputFormat.setOutputPath(job, new Path("/root/wcout"));
		
		//submit
		job.waitForCompletion(true);
	}

}
