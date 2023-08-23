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
        System.out.println(calculateAnomalyScore(100000));
		System.out.println(calculateAnomalyScore(10000));
        System.out.println(calculateAnomalyScore(5000));
        System.out.println(calculateAnomalyScore(1000));
        System.out.println(calculateAnomalyScore(500));
 	}

 	private static double calculateAnomalyScore(float value){
        double score = Double.parseDouble("0");
        RandomCutForest forest = RandomCutForest.builder()
        .numberOfTrees(5)
        .sampleSize(5)
        .dimensions(1)
        .timeDecay(100000)
        .shingleSize(1)
        .build();    
        double valueLen = (Float.toString(value)).length() * 100;
        for (int i=0; i<valueLen; i++){
            for (int j=0; j<(valueLen/100); j++){                    
                float[] point = new float[]{value * j};
                score = forest.getAnomalyScore(point);
                forest.update(point);
            }
        }
        return score;
    }
}
