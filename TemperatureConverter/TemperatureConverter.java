package TemperatureConverter;

import java.util.Scanner;

public class TemperatureConverter {

    public static double convertFahrenheitToDegreesCelsius(double temperatureInFahrenheit) {
        return (temperatureInFahrenheit - TemperatureConverterConstants.CONVERSION_CONSTANT) * TemperatureConverterConstants.FAHRENHEIT_CONVERSION_CONSTANT;
    }

    public static double convertDegreesCelsiusToFahrenheit(double temperatureInDegrees) {
        return (temperatureInDegrees * TemperatureConverterConstants.DEGREES_CONVERSION_CONSTANT) + TemperatureConverterConstants.CONVERSION_CONSTANT;
    }

    public static void printTemperatureMessage(double initialTemperature, char initialMetric,
                                                    double newTemperature, char convertedMetric) {
        System.out.println(String.format("Initial temperature of %.2f in %c was converted to %.2f in %c.\n",
                initialTemperature, initialMetric, newTemperature, convertedMetric));
    }

    public static boolean validateInputMetric(char inputTempMetric) {
        if (inputTempMetric == TemperatureConverterConstants.FAHRENHEIT_CHAR) {
            return true;
        }

        if (inputTempMetric == TemperatureConverterConstants.DEGREE_CHAR) {
            return true;
        }
        return false;
    }

    public static boolean validateNegativeTemperature(String temperature) {
        if (temperature.charAt(0) == TemperatureConverterConstants.NEGATIVE) {
            return false;
        }
        return true;
    }

    public static double performTemperatureConversion(String conversionType, double temperatureToConvert) {
        double convertedTemp;
        switch (conversionType) {
            case TemperatureConverterConstants.FAHRENHEIT_TO_DEGREE:
                convertedTemp = convertFahrenheitToDegreesCelsius(temperatureToConvert);
                break;
            case TemperatureConverterConstants.DEGREE_TO_FAHRENHEIT:
                convertedTemp = convertDegreesCelsiusToFahrenheit(temperatureToConvert);
                break;
            default:
                convertedTemp = temperatureToConvert;
        }
        return convertedTemp;
    }

    public static boolean isQuit(String quitKey) {
        if (quitKey.equalsIgnoreCase(TemperatureConverterConstants.QUIT_KEY)) {
            return true;
        }
        return false;
    }

    public static char readAndValidateMetricFromUser(String message) {
        Scanner in = new Scanner(System.in);
        char metric = 0;
        while (true) {
            System.out.println(message);
            System.out.println("Enter to/from:");
            String metricInput = in.next();
            if (isQuit(metricInput.trim())) {
                System.exit(0);
            }

            metric = metricInput.charAt(0);
            if (validateInputMetric(metric)) {
                break;
            }
        }
        return metric;
    }

    public static double readAndValidateTemperature() {
        Scanner in = new Scanner(System.in);
        double tempToConvert = 0;

        while (true) {
            try {
                System.out.println("Please enter temperature to convert: ");
                String tempInput = in.next();

                if (isQuit(tempInput.trim())) {
                    System.exit(0);
                }

                if (validateNegativeTemperature(tempInput)) {
                    tempToConvert = Double.parseDouble(tempInput);
                    break;
                } else {
                    throw new NumberFormatException();
                }

            } catch (NumberFormatException e) {
                System.out.println("Please re-enter a valid temperature to convert.\n");
            }
        }
        return tempToConvert;
    }

    public static void main (String[]args){
        while(true) {
            char fromMetric = readAndValidateMetricFromUser("from");
            char toMetric = readAndValidateMetricFromUser("to");
            double tempToConvert = readAndValidateTemperature();
            String temperatureConversionCombination = fromMetric + String.valueOf(toMetric);
            double convertedTemp = performTemperatureConversion(temperatureConversionCombination, tempToConvert);
            printTemperatureMessage(tempToConvert, fromMetric, convertedTemp, toMetric);
        }
    }
}