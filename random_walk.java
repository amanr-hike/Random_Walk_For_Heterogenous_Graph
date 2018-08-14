import java.io.*;
import java.util.*;
import java.util.concurrent.*;

class graph
{
	Map<Integer,ArrayList<ArrayList<ArrayList<Integer>>>> G;
	Map<String,Integer> node_info;
	Map<Integer,Integer> node_type;
	Map<String,Float> edge_type_prob;
	static int edge_type ;
	// Map<>
	public graph()
	{
		this.G = new HashMap<Integer,ArrayList<ArrayList<ArrayList<Integer>>>>();
		this.node_info = new HashMap<String,Integer>();
		this.edge_type_prob = new HashMap<String,Float>();
		this.node_type = new HashMap<Integer,Integer>();
		// this.edge_type = 0;
	}

	public void pre_load_edgeList()
	{
		ArrayList<Integer> keys = new ArrayList<Integer>(this.node_type.keySet());
		for(int i=0;i<keys.size();i++)
		{
			this.G.put(keys.get(i),new ArrayList<ArrayList<ArrayList<Integer>>>());
			for(int j=0;j<this.edge_type;j++)
			{
				this.G.get(keys.get(i)).add(new ArrayList<ArrayList<Integer>>());
				this.G.get(keys.get(i)).get(j).add(new ArrayList<Integer>());
				this.G.get(keys.get(i)).get(j).add(new ArrayList<Integer>());
			}
			// System.out.println(this.G.get(keys.get(i)).size() +" "+"size");
		}
		// System.out.println(this.node_type.size());
	}

	public void load_edgeList(String file_,boolean weighted,boolean undirected) throws FileNotFoundException , IOException
	{
		pre_load_edgeList();
		File dir = new File(file_);
		File[] fileslist = dir.listFiles();
		for(File file : fileslist)
		{
			if(file.isFile() && file.getName().endsWith(".txt"))
			{
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String s = br.readLine();
				if (weighted)
				{	
					while(s!=null)
					{
						String [] x = s.trim().split(" ");
						int node1 = Integer.parseInt(x[0]);
						int node2 = Integer.parseInt(x[1]);
						int index1 = this.node_type.get(node1);
						int index2 = this.node_type.get(node2);
						ArrayList<ArrayList<Integer>> temp1 = this.G.get(node1).get(index2);
						temp1.get(0).add(node2);

						if (temp1.get(1).size()==0)
						{
							temp1.get(1).add(Integer.parseInt(x[2]));
						}
						else
						{
							temp1.get(1).add(temp1.get(1).get(temp1.get(1).size()-1)+Integer.parseInt(x[2]));

						}
						if (undirected)
						{
							ArrayList<ArrayList<Integer>> temp2 = this.G.get(node2).get(index1);
							temp2.get(0).add(node1);
							if (temp2.get(1).size()==0)
							{
								temp2.get(1).add(Integer.parseInt(x[2]));
							}
							else
							{
								temp2.get(1).add(temp2.get(1).get(temp2.get(1).size()-1)+Integer.parseInt(x[2]));

							}
						}
						s = br.readLine();
					}
				}
				else
				{
					while(s!=null)
					{
						String [] x = s.trim().split(" ");
						int node1 = Integer.parseInt(x[0]);
						int node2 = Integer.parseInt(x[1]);
						// System.out.println(node1 +" "+node2);
						int index1 = this.node_type.get(node1);
						int index2 = this.node_type.get(node2);
						// System.out.println(index1 +" "+index2);
						ArrayList<ArrayList<Integer>> temp1 = this.G.get(node1).get(index2);
						temp1.get(0).add(node2);

						if (temp1.get(1).size()==0)
						{
							temp1.get(1).add(1);
						}
						else
						{
							temp1.get(1).add(temp1.get(1).get(temp1.get(1).size()-1)+1);

						}
						if (undirected)
						{
							ArrayList<ArrayList<Integer>> temp2 = this.G.get(node2).get(index1);
							temp2.get(0).add(node1);
							if (temp2.get(1).size()==0)
							{
								temp2.get(1).add(1);
							}
							else
							{
								temp2.get(1).add(temp2.get(1).get(temp2.get(1).size()-1)+1);

							}
						}
						s = br.readLine();
					}	
				}
				br.close();
			}
		}
		// ArrayList<Integer> keys = new ArrayList<Integer>(this.G.keySet());
		// for(int i=0;i<keys.size();i++)
		// {
		// 	System.out.println(keys.get(i)+" Node");
		// 	for(int j=0;j<this.G.get(keys.get(i)).size();j++)
		// 	{
		// 		for(int k=0;k<this.G.get(keys.get(i)).get(j).size();k++)
		// 		{
		// 			for(int l = 0;l<this.G.get(keys.get(i)).get(j).get(k).size();l++)
		// 			{
		// 				System.out.print(this.G.get(keys.get(i)).get(j).get(k).get(l)+" ");
		// 			}
		// 			System.out.println();
		// 		}
		// 	}
		// }
	}

	public void load_nodeinfo(String file_,boolean node_info) throws FileNotFoundException,IOException
	{
		if (node_info)
		{	
			int count = 0;
			FileReader f = new FileReader(file_);
			BufferedReader br = new BufferedReader(f);
			String s = br.readLine();
			while(s!=null)
			{
				this.node_info.put(s.trim(),count);
				count += 1;
				s = br.readLine();
			}
			br.close();
			// ArrayList<String> keys = new ArrayList<String>(this.node_info.keySet());
			// for(int i=0;i<keys.size();i++)
			// {
			// 	System.out.println(keys.get(i) + " "+this.node_info.get(keys.get(i)));
			// }
		}
		else
		{
			this.node_info.put("Single_Edge",0);
		}

	}
	public void load_nodetype (String file_, boolean is_nodetype) throws FileNotFoundException,IOException
	{
		// System.out.println(2);
		FileReader f = new FileReader(file_);
		BufferedReader br = new BufferedReader(f);
		String s = br.readLine();
		if (is_nodetype)
		{
			while(s!=null)
			{
				StringTokenizer st = new StringTokenizer(s);
				this.node_type.put(Integer.parseInt(st.nextToken()),this.node_info.get(st.nextToken()));
				s = br.readLine();
			}
			// ArrayList<Integer> keys = new ArrayList<Integer>(this.node_type.keySet());
			// for(int i=0;i<keys.size();i++)
			// {
			// 	System.out.println(keys.get(i) + " "+this.node_type.get(keys.get(i)));
			// }

		}
		else
		{
			while(s!=null)
			{
				this.node_type.put(Integer.parseInt(s.trim()),0);
				s = br.readLine();
			}	
		}
	}

	public void load_edgeprob(String file_, boolean edge_prob) throws IOException, FileNotFoundException
	{
		if(edge_prob)
		{
			FileReader f = new FileReader(file_);
			BufferedReader br = new BufferedReader(f);
			String s = br.readLine();
			while(s!=null)
			{
				String [] arg = s.trim().split(" ");
				String key = String.valueOf(arg[0]) +" "+ String.valueOf(arg[1]);
				String key1 = String.valueOf(arg[1]) +" "+ String.valueOf(arg[0]);
				this.edge_type_prob.put(key,Float.parseFloat(arg[2]));
				s = br.readLine();
				// this.edge_type += 1;
			}
			// ArrayList<String> keys = new ArrayList<String>(this.edge_type_prob.keySet());
			// for(int i=0;i<keys.size();i++)
			// {
			// 	System.out.println(keys.get(i) + " "+this.edge_type_prob.get(keys.get(i)));
			// }
		}
		else
		{
			this.edge_type_prob.put("0 0",1f);
			// this.edge_type = 1;
		}
	}

	public int find_edgetype(int start)
	{
		String node = String.valueOf(this.node_type.get(start));
		float sum = 0;
		for(int i=0;i<this.edge_type;i++)
		{
			sum += this.edge_type_prob.get(node + " "+i);
		}
		Random rand = new Random();
		float num = rand.nextFloat()*sum;
		sum = 0; 
		int index = 0;
		for(int i=0;i<this.edge_type;i++)
		{
			sum += this.edge_type_prob.get(node + " "+i);
			if(sum>=num)
			{
				index = i; 
				break;
			}
		}
		return index;
	}

	public int binary_search(ArrayList<Integer> arr, int x)
    {
        int l = 0, r = arr.size() - 1;
        while (l <= r)
        {
            int m = l + (r-l)/2;
 
            // Check if x is present at mid
            if (arr.get(m) == x)
                return m;
 
            // If x greater, ignore left half
            if (arr.get(m) < x)
                l = m + 1;
 
            // If x is smaller, ignore right half
            else
                if (m>0 && arr.get(m-1)<x) {
                    return m-1;
                }
                else
                r = m - 1;
        }
        return 0;  
    }

	public ArrayList<Integer> random_walk(int path_length, int start)
	{
		ArrayList <Integer> path = new ArrayList<Integer>();
		path.add(start);
		while(path.size() < path_length)
		{
			int node = path.get(path.size()-1);
			int index = find_edgetype(node);
			if(this.G.get(node).get(index).get(0).size()==0)
			{
				continue;
			}
			else
			{
				ArrayList<ArrayList<Integer>> temp1 = this.G.get(node).get(index);
				Random rand = new Random();
				int num1 = rand.nextInt(temp1.get(1).get(temp1.get(1).size()-1));
				int index1 = binary_search(temp1.get(1),num1);
				path.add(temp1.get(0).get(index1));
			}
		}
		return path;
	}
	// public void add_nodestype(File file_)
}

public class random_walk implements Runnable
{
	static graph main_graph;
	static ArrayList<Integer> nodes = new ArrayList<Integer>();
	String file;
	String temp;
	int num_path = 10 ;
	int path_length = 40;
	public random_walk()
	{
		file = "file.txt";
		temp = "0:";
	}
	public random_walk(String file, String temp)
	{
		this.file = file;
		this.temp = temp;
	}
	public void build_deepwalk_corpus() 
	{
		ArrayList<ArrayList<Integer>> walks = new ArrayList<ArrayList<Integer>>();
		int count = 0;
		String [] ar1 = new String[2];
		int [] ar = new int[2];
		ar1 = this.temp.split(" ");
		System.out.println(this.temp);
		ar[0] = Integer.parseInt(ar1[0]);
		ar[1] = Integer.parseInt(ar1[1]);
		for(int i=0;i<this.num_path;i++)
		{
			for(int j=ar[0]-1;j<ar[1];j++)
			{
				// System.out.println(ar[0] +" "+ar[1]);
				walks.add(main_graph.random_walk(this.path_length,this.nodes.get(j)));
				count += 1;
			}
			if(count >= 20000)
			{
				System.out.println("hello");
				try
				{
					File f = new File(file);
					FileWriter fr = new FileWriter(f);
					BufferedWriter br = new BufferedWriter(fr);
					for (int k=0;k<walks.size();k++)
					{
						//System.out.print(walks.get(i).get(0));
						System.out.println(String.valueOf(walks.get(k).get(0)));
						br.write(walks.get(k).get(0));
						for(int l=1;l<walks.get(k).size();l++)
						{
							br.write(" "+walks.get(k).get(l));
							//System.out.print(" "+walks.get(i).get(j));
						}
						br.write("\n");
						//System.out.println();
					}
					System.out.println("done");
					walks = new ArrayList<ArrayList<Integer>>();
					br.close();
					count = 0;
				}
				catch(IOException e)
				{
					System.out.println("error");
				}
			}
		}
		try
		{
			File f = new File(file);
			FileWriter fr = new FileWriter(f);
			BufferedWriter br = new BufferedWriter(fr);
			for (int k=0;k<walks.size();k++)
			{
				//System.out.print(walks.get(i).get(0));
				///System.out.println(1);
				br.write(String.valueOf(walks.get(k).get(0)));
				for(int l=1;l<walks.get(k).size();l++)
				{
					br.write(" "+walks.get(k).get(l));
					//System.out.print(" "+walks.get(i).get(j));
				}
				br.write("\n");
				//System.out.println();
			}
			System.out.println("done");
			br.close();
		}
		catch(IOException e)
		{
			System.out.println("error");
		}
	}

	public void run()
	{
		this.build_deepwalk_corpus();
	}
	// arg[0] = inputfile
	public static void main(String [] args) throws IOException,InterruptedException
	{
		random_walk m = new random_walk();
		m.main_graph = new graph();
		m.main_graph.load_nodeinfo("nodeinfo.txt",true);
		m.main_graph.load_nodetype("nodetype.txt",true);
		m.main_graph.edge_type = m.main_graph.node_info.size();
		m.main_graph.load_edgeprob("edge_prob.txt",true);
		m.main_graph.load_edgeList("graph/.",true,true);
		m.nodes = new ArrayList<Integer>(m.main_graph.G.keySet());
		Collections.sort(m.nodes);
		// System.exit(0);
		int size = m.main_graph.G.size();
		int num_of_Workers = 2;
		int start = 1;
		int last = size/num_of_Workers;
		int count = 1;
		ArrayList<Runnable> threads = new ArrayList<Runnable>();
		ExecutorService exec = Executors.newFixedThreadPool(num_of_Workers);
		for(int i=0;last<=size;i++)
		{
			System.out.println(start + " "+last);
			// System.exit(0);
			Runnable m1 = new random_walk(("walk"+(i+1)+".txt"),(start + " "+last));
			exec.execute(m1);
			start = last+1;
			if (last == size)
				break;
			if(last+size/num_of_Workers > size)
				last = size;
			else
				last += size/num_of_Workers;
		}
		if (!exec.isTerminated())
		{
			exec.shutdown();
			exec.awaitTermination(5L,TimeUnit.SECONDS);
		}


		
	}

}