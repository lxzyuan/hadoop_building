public class HDFSDemo {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Configuration conf = new Configuration();
		conf.set("dfs.nameservices", "ns1");
		conf.set("dfs.ha.namenodes.ns1","nn1,nn2");
		conf.set("dfs.namenode.rpc-address.ns1.nn1","192.168.1.201:9000");
		conf.set("dfs.namenode.rpc-address.ns1.nn2","192.168.1.202:9000");
		conf.set("dfs.client.failover.proxy.provider.ns1","org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
		FileSystem fs = FileSystem.get(new URI("hdfs://ns1"), conf);		
		InputStream in = new FileInputStream ("//root//m2.tar.gz");
		OutputStream out = fs.create(new Path("/m2.tar.gz"));
		IOUtils.copyBytes(in, out, 4096, true);
	}
}
