package cn.itcast.hadoop.mr.dc;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DataCount {

	public static class DCMapper extends Mapper<LongWritable, Text, Text, DataBean>{

		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			//accept 
			String line = value.toString();
			//split
			String[] fields = line.split("\t");
			String tel = fields[1];
			long up = Long.parseLong(fields[8]);
			long down = Long.parseLong(fields[9]);	
			DataBean bean = new DataBean(tel, up, down);
			//send
			context.write(new Text(tel), bean);
		}
		
	}
	
	public static class DCReducer extends Reducer<Text, DataBean, Text, DataBean>{

		@Override
		protected void reduce(Text key, Iterable<DataBean> values, Context context)
				throws IOException, InterruptedException {
			long up_sum = 0;
			long down_sum = 0;
			for(DataBean bean : values){
				up_sum += bean.getUpPayLoad();
				down_sum += bean.getDownPayLoad();
			}
			DataBean bean = new DataBean("", up_sum, down_sum);
			context.write(key, bean);
		}
		
		
	}
	
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		
		job.setJarByClass(DataCount.class);
		
		job.setMapperClass(DCMapper.class);
		//k2 v2 and k3 v3 类型一一对应时，可省略
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(DataBean.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		
		job.setReducerClass(DCReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DataBean.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.waitForCompletion(true);
		
	}
	

}
