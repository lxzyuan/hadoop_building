package cn.itcast.hadoop.mr;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WCMapper extends Mapper<LongWritable, Text, Text, LongWritable>{

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		//accept
		String line = value.toString();
		//split
		String[] words = line.split(" ");
		//loop
		for(String w : words){
			//send
			context.write(new Text(w), new LongWritable(1));
		}
	}

	
	
	

}
