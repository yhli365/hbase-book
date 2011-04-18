package admin;

// cc ClusterStatusExample Example reporting the status of a cluster
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.ClusterStatus;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HServerInfo;
import org.apache.hadoop.hbase.HServerLoad;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Map;

public class ClusterStatusExample {

  public static void main(String[] args) throws IOException, InterruptedException {
    Configuration conf = HBaseConfiguration.create();

    // vv ClusterStatusExample
    HBaseAdmin admin = new HBaseAdmin(conf);

    ClusterStatus status = admin.getClusterStatus(); // co ClusterStatusExample-1-GetStatus Get the cluster status.

    System.out.println("Cluster Status:\n--------------");
    System.out.println("Avg Load: " + status.getAverageLoad());
    System.out.println("HBase Version: " + status.getHBaseVersion());
    System.out.println("Version: " + status.getVersion());
    System.out.println("No. Servers: " + status.getServers());
    System.out.println("No. Dead Servers: " + status.getDeadServers());
    System.out.println("Dead Servers: " + status.getDeadServerNames());
    System.out.println("No. Regions: " + status.getRegionsCount());
    System.out.println("No. Requests: " + status.getRequestsCount());

    System.out.println("\nServer Info:\n--------------");
    for (HServerInfo info : status.getServerInfo()) { // co ClusterStatusExample-2-ServerInfo Iterate over the included server info instances.
      System.out.println("Hostname: " + info.getHostname());
      System.out.println("RPC Port: " + info.getHostnamePort());
      System.out.println("Servername: " + info.getServerName());
      System.out.println("Info Port: " + info.getInfoPort());
      System.out.println("Address: " + info.getServerAddress());
      System.out.println("Start Code: " + info.getStartCode());

      HServerLoad load = info.getLoad(); // co ClusterStatusExample-3-ServerLoad Retrieve the load details for the current server.

      System.out.println("\nServer Load:\n--------------");
      System.out.println("Load: " + load.getLoad());
      System.out.println("Max Heap (MB): " + load.getMaxHeapMB());
      System.out.println("Memstore Size (MB): " + load.getMemStoreSizeInMB());
      System.out.println("No. Regions: " + load.getNumberOfRegions());
      System.out.println("No. Requests: " + load.getNumberOfRequests());
      System.out.println("Storefile Index Size (MB): " + load.getStorefileIndexSizeInMB());
      System.out.println("No. Storefiles: " + load.getStorefiles());
      System.out.println("Storefile Size (MB): " + load.getStorefileSizeInMB());
      System.out.println("Used Heap (MB): " + load.getUsedHeapMB());

      System.out.println("\nRegion Load:\n--------------");
      for (Map.Entry<byte[], HServerLoad.RegionLoad> entry : // co ClusterStatusExample-4-Regions Iterate over the region details of the current server.
        load.getRegionsLoad().entrySet()) {
        System.out.println("Region: " + Bytes.toStringBinary(entry.getKey()));

        HServerLoad.RegionLoad regionLoad = entry.getValue(); // co ClusterStatusExample-5-RegionLoad Get the load details for the current region.

        System.out.println("Name: " + Bytes.toStringBinary(regionLoad.getName()));
        System.out.println("No. Stores: " + regionLoad.getStores());
        System.out.println("No. Storefiles: " + regionLoad.getStorefiles());
        System.out.println("Storefile Size (MB): " + regionLoad.getStorefileSizeMB());
        System.out.println("Storefile Index Size (MB): " + regionLoad.getStorefileIndexSizeMB());
        System.out.println("Memstore Size (MB): " + regionLoad.getMemStoreSizeMB());
        System.out.println("No. Requests: " + regionLoad.getRequestsCount());
        System.out.println("No. Read Requests: " + regionLoad.getReadRequestsCount());
        System.out.println("No. Write Requests: " + regionLoad.getWriteRequestsCount());
        System.out.println();
      }
    }
    // ^^ ClusterStatusExample
  }
}
