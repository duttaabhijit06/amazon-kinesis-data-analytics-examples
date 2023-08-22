package software.amazon.flink.example;

import org.apache.flink.table.functions.ScalarFunction;
import com.amazon.randomcutforest.RandomCutForest;

public class RandomCutForestUDF extends ScalarFunction{
    
    public double eval(String todo, float value) throws Exception{
        
        double retVal;
        switch(todo) {
            case "random_cut_forest": retVal = calculateAnomalyScore(value); break;
            default: retVal = 0;
        }
        return retVal;
    }
    public static void main (String[] args) throws java.lang.Exception
	{   
		System.out.println(calculateAnomalyScore(6));
 	}

 	private static double calculateAnomalyScore(float value){
        RandomCutForest forest = RandomCutForest.builder()
        .numberOfTrees(5)
        .sampleSize(10)
        .dimensions(1)
        .timeDecay(100000)
        .shingleSize(1)
        .build();    
                            
        float[] point = new float[]{value};
        double score = forest.getAnomalyScore(point);
        forest.update(point);
        return score;
    }
}
