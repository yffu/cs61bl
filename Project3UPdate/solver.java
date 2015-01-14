
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.HashMap;
public class solver{
	
	public static Tray initial;
	public static ArrayList<int[]> goals; // pick one goal!
	public static HashMap<Integer,ArrayList<Tray>> table; 
	public static moves mymoves;
	public static boolean debugging=true;
	
	public void initialize(String initialtext, String goaltext) throws Exception{
		Scanner lines= new Scanner(initialtext).useDelimiter("\n");
		Scanner size= new Scanner(lines.next());
		int height = size.nextInt();
		int width= size.nextInt();
		ArrayList<int[]> blocks = new ArrayList<int[]>();
		while (lines.hasNext()){
			int[] newBlock= new int[4];
			Scanner blockdata= new Scanner(lines.next());
			for(int a=0;a<4;a++){
				newBlock[a]=blockdata.nextInt();
			}
			blocks.add(newBlock);
		}

		initial= new Tray(height,width,blocks);

		
		goals= new ArrayList<int[]>();
		Scanner goallines=new Scanner(goaltext).useDelimiter("\n");
		while (goallines.hasNext()){
			int[] newBlock= new int[4];
			Scanner goaldata= new Scanner(goallines.next());
			for(int a=0;a<4;a++){
				newBlock[a]=goaldata.nextInt();
			}
			goals.add(newBlock);
		}

	}
	public static void print(int[] a){
		System.out.println(a[0]+" "+a[1]+" "+(a[0]-a[4])+" "+(a[0]-a[5]));
	}

	public static void output(node x){
		 ArrayList<node> seq=new ArrayList<node>();
		while (x.prev!=null){
			seq.add(x);
			x=x.prev;
		}
		while (!seq.isEmpty()){
			print(seq.get(seq.size()-1).move);
			seq.remove(seq.get(seq.size()-1));
		} 
	}
	public static boolean hasbeenseen(HashMap<Integer,ArrayList<Tray>> table,Tray a){
		if  (!table.containsKey(a.hashCode())) return false;
		else {
			ArrayList<Tray> b=table.get(a.hashCode());
			Iterator iter=b.iterator();
			 while(iter.hasNext())
			 {Tray temp=(Tray)iter.next();
			  if( temp.equals(a))
				 return true;}
			return false;}
	}
	public static void put(HashMap<Integer,ArrayList<Tray>> table,Tray temp){
		ArrayList<Tray> t=new ArrayList<Tray>();
		if  (!table.containsKey(temp.hashCode())){
			t.clear();
			t.add(temp);
		table.put(temp.hashCode(),t);		 }
		else{
			table.get(temp.hashCode()).add(temp);
		}
		
	}
	public static void solve(Tray initial, ArrayList<int[]> goals){
		
		table=new HashMap<Integer,ArrayList<Tray>>();
		mymoves=new moves();
		mymoves.front=new node();
		mymoves.end=new node();
		mymoves.myhead=initial;
		mymoves.front.current=initial;
		if (mymoves.front.current.goalCheck(goals)) System.exit(1);
		mymoves.end=mymoves.front;
		
		
		put(table,initial);	
		while (!mymoves.end.current.goalCheck(goals)){

			Iterator iter=mymoves.front.current.getMoves();
			while (iter.hasNext()){
				int[] move=(int[]) iter.next();
				Tray temp=mymoves.front.current.makeNextTray(move);
				if (!temp.equals(mymoves.front.prev)&&!hasbeenseen(table,temp)){
					mymoves.end.Rest=new node();
				mymoves.end.Rest.current=temp;
				mymoves.end.Rest.prev=mymoves.front;
				mymoves.end.Rest.move=move;
				put(table,temp);
				mymoves.end=mymoves.end.Rest;
				if (mymoves.end.current.goalCheck(goals)) {output(mymoves.end);
				break;
				}
				}
			}
			mymoves.front=mymoves.front.Rest;	   	   
		}
		if (mymoves.front.equals(mymoves.end)) System.exit(1);
		
	}
  
	public static void main(String[] args) throws Exception{
		solver sol = new solver();	
		
		if (!debugging) {
		
		if (args.length!=2) throw new Exception("wrong file");
		sol.initialize(args[0],args[1]);}
		
		solve(initial,goals);
		
	}
}