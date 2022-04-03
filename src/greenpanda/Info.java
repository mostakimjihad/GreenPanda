package greenpanda;

public class Info {
    
    static int price;
    static int payment;
    static int[] weight = {1, 1, 1, 1, 1, 1};
    static int[] value = {50, 30, 40, 35, 67, 45};
    static int[][] graph = new int[6][6];
    static int[][] dp = new int[7][3];
    static int vertex;
    static boolean[] processed = new boolean[6];
    static int[] value1 = new int[6];
    static int[] parent = new int[6];
    static int max(int num1, int num2){

        if(num1 > num2)
            return num1;
        else
            return num2;
    }
    static int napsack(){
        
        for(int i = 0; i <= 6; i++){

            for(int j = 0; j <= 2; j++){

                if( i == 0 || j == 0){

                    dp[i][j] = 0;
                }
                else if(weight[i-1] <= j){

                    dp[i][j] = max(dp[i-1][j], value[i-1] + dp[i-1][j-weight[i-1]]);
                }
                else{
                    dp[i][j] = dp[i-1][j];
                }
            }
        }

        return dp[6][2];
    }
    static void addEdge(int source, int desti, int weight){
        graph[source][desti] = weight;
        graph[desti][source] = weight;
    } 
    static void initi(){
        addEdge(0,1,2);
        addEdge(1,5,3);
        addEdge(1,2,4);
        addEdge(2,5,4);
        addEdge(2,3,2);
        addEdge(3,4,3);
        addEdge(4,5,1);
    }
    
    static int selectMinVertex()
{
    int minimum = Integer.MAX_VALUE;
    for(int i=0;i<6;++i)
    {
        if(processed[i] == false && value1[i]<minimum)
        {
            vertex = i;
            minimum = value1[i];
        }
    }
    return vertex;
}

static void dijkstra()
{
    for (int i = 0; i < 6; i++){
        parent[i] = -1;
        value1[i] = Integer.MAX_VALUE;
        processed[i] = false;
    }   //TRUE->Vertex is processed

    //Assuming start point as Node-0
    parent[0] = -1; //Start node has no parent
    value1[0] = 0;   //start node has value=0 to get picked 1st

    //Include (V-1) edges to cover all V-vertices
    for(int i=0;i<5;++i)
    {
        //Select best Vertex by applying greedy method
        int U = selectMinVertex();
        processed[U] = true;    //Include new Vertex in shortest Path Graph

        //Relax adjacent vertices (not yet included in shortest path graph)
        for(int j=0;j<6;++j)
        {
            /* 3 conditions to relax:-
                  1.Edge is present from U to j.
                  2.Vertex j is not included in shortest path graph
                  3.Edge weight is smaller than current edge weight
            */
            if(graph[U][j]!=0 && processed[j]==false && value1[U]!=Integer.MAX_VALUE
            && (value1[U]+graph[U][j] < value1[j]))
            {
                value1[j] = value1[U]+graph[U][j];
                parent[j] = U;
            }
        }
    }
    //Print Shortest Path Graph
    for(int i=1;i<6;++i)
        System.out.println("U->V: "+parent[i]+"->"+i+"  wt = "+graph[parent[i]][i]+"\n");
}
}
