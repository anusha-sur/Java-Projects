import java.util.Scanner;

public class TemperatureConverter {

    private static final double CONVERSION_CONSTANT = 32;
    private static final double DEGREES_CONVERSION_CONSTANT = 9/5;
    private static final double FAHRENHEIT_CONVERSION_CONSTANT = 5/9;
    private static final char FAHRENHEIT_CHAR = 'F';
    private static final char DEGREE_CHAR = 'C';

    public static double convertFahrenheitToDegrees(double temperatureInFahrenheit){
        return (temperatureInFahrenheit - CONVERSION_CONSTANT) * FAHRENHEIT_CONVERSION_CONSTANT;
    }

    public static double convertDegreesToFahrenheit(double temperatureInDegrees){
        return (temperatureInDegrees * DEGREES_CONVERSION_CONSTANT) + CONVERSION_CONSTANT;
    }

    public static void printValidTemperatureMessage(double initialTemperature, char initialMetric, double newTemperature, char convertedMetric ){
        System.out.println(String.format("Initial temperature of %.2f in %c was converted to %.2f in %c.\n",initialTemperature,initialMetric,newTemperature,convertedMetric));
    }

    public static boolean validateInputMetric(char inputTempMetric){

       if(inputTempMetric == FAHRENHEIT_CHAR){
           return true;
       }

       if(inputTempMetric == DEGREE_CHAR){
           return true;
       }
       return false;
    }

    public static boolean validateNegativeTemperature(String temp){
        if(temp.charAt(0) == '-'){
            return false;
        }
        return true;
    }

    public static double performConversion(String conversionType, double tempToConvert){
        double convertedTemp;
        switch(conversionType){
            case "FC":
                convertedTemp = convertFahrenheitToDegrees(tempToConvert);
                break;
            case "CF":
                convertedTemp = convertDegreesToFahrenheit(tempToConvert);
                break;
            default:
                convertedTemp = tempToConvert;
        }
        return convertedTemp;
    }

    public static boolean checkForQuit(String quitKey){
        if(quitKey.equalsIgnoreCase("Q")){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean stopFlag = true;
        boolean validTemperatureFlag = true;
        boolean validMetricsFlag = true;
        boolean continueFlag = true;
        char fromMetric = 0;
        char toMetric = 0;
        double tempToConvert = 0;
        double convertedTemp = 0;

        while(stopFlag){

            //Validates Input Metrics.
            while(validMetricsFlag && continueFlag){
                boolean validFromMetric;
                boolean validToMetric;
                System.out.println("Please enter metric to convert from: ");
                System.out.println("Valid Metrics: F - Fahrenheit, C - Celsius");
                System.out.println("To quit the application, press Q/q.");

                String input = in.next();
                if(checkForQuit(input.trim())){
                    continueFlag = false;
                    stopFlag = false;
                    break;
                }

                fromMetric = input.charAt(0);
                validFromMetric = validateInputMetric(fromMetric);

                System.out.println("Please enter metric to convert to: ");
                input = in.next();
                if(checkForQuit(input.trim())){
                    continueFlag = false;
                    stopFlag = false;
                    break;
                }

                toMetric = input.charAt(0);
                if(checkForQuit(input.trim())){
                    stopFlag = false;
                    break;
                }

                validToMetric = validateInputMetric(toMetric);
                if(validFromMetric && validToMetric){
                    validMetricsFlag = false;
                } else{
                    System.out.println("Please re-enter valid input\n");
                }
            }

            //Validates Temperature
            while(validTemperatureFlag && continueFlag){
                try{
                    System.out.println("Please enter temperature to convert: ");
                    String tempInput = in.next();
                    if(checkForQuit(tempInput.trim())){
                        continueFlag = false;
                        stopFlag = false;
                        break;
                    }

                    if(validateNegativeTemperature(tempInput)){
                        tempToConvert = Double.parseDouble(tempInput);
                        validTemperatureFlag = false;

                    } else{
                        throw new NumberFormatException();
                    }
                } catch(NumberFormatException nfe){
                    System.out.println("Please re-enter a valid temperature to convert.\n");
                }
            }

            //Once everything is valid, perform conversions.
            if(continueFlag){
                String temperatureConversionCombination = new StringBuilder().append(fromMetric).append(toMetric).toString();
                convertedTemp = performConversion(temperatureConversionCombination, tempToConvert);
                printValidTemperatureMessage(tempToConvert,fromMetric,convertedTemp,toMetric);
            }
            validMetricsFlag = true;
            validTemperatureFlag = true;
        }
    }
}
