package software.amazon.flink.example;

import org.apache.flink.table.functions.ScalarFunction;
import com.amazon.randomcutforest.RandomCutForest;

public class RandomCutForestUDF extends ScalarFunction{
    
    public double eval(float value) throws Exception{
        
        double retVal = Double.parseDouble("0");
        retVal = calculateAnomalyScore(value); 
        if (retVal>0){
            return retVal;
        }else{
            return Double.parseDouble("0");
        }
    }
    public static void main (String[] args) throws java.lang.Exception
	{ 
        int[] values = {1000000, 100000, 10000, 1000, 100, 10};
        for (int value : values){
            System.out.println(value + ":" + calculateAnomalyScore(value));
        }
 	}

 	private static double calculateAnomalyScore(float value){
        double score = Double.parseDouble("0");
        RandomCutForest forest = RandomCutForest.builder()
        .numberOfTrees(5)
        .sampleSize(10)
        .dimensions(1)
        .timeDecay(100000)
        .shingleSize(1)
        .build();    
        double valueLen = (Float.toString(value)).length();
        double loopCounter = (((value-valueLen)*10)+valueLen)/valueLen;
        for (int i=0; i<loopCounter; i++){
            // for (int j=0; j<(valueLen/100)/5; j++){                    
            float[] point = new float[]{i};
            score = forest.getAnomalyScore(point);
            forest.update(point);
            // }
        }
        return score;
    }
}
