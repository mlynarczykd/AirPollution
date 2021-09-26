package labp;

import org.apache.commons.cli.*;

/**
 * Klasa wywołująca potrzebne metody
 */

public class Main {

    public static void main(String[] args){
        DefaultParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption("f1", true, "Nazwa stacji.")
                .addOption(Option.builder("f2").hasArgs().desc("Nazwa stacji, nazwa parametru i data (format: dd.MM.yyyy HH:mm).").build())
                .addOption(Option.builder("f3").hasArgs().desc("Nazwa stacji, nazwa parametru, data początkowa i data końcowa (format dat: dd.MM.yyyy HH:mm).").build())
                .addOption(Option.builder("f4").hasArgs().desc("Data (format: dd.MM.yyyy HH:mm) i nazwy stacji.").build())
                .addOption(Option.builder("f5").hasArg().desc("Data (format: dd.MM.yyyy HH:mm).").build())
                .addOption(Option.builder("f6").hasArgs().desc("Nazwa stacji, data (format: dd.MM.yyyy HH:mm) i liczba stanowisk do wypisania.").build())
                .addOption("f7", true, "Nazwa parametru.")
                .addOption(Option.builder("f8").hasArgs().desc("Nazwa parametru, data początkowa, data końcowa (format dat: dd.MM.yyyy HH:mm) i nazwy stacji.").build())
                .addOption("h","help",false, "Pokaż pomoc.");
        HelpFormatter formatter = new HelpFormatter();
        Facade facade = new Facade();
        try {
            CommandLine cmdLine = parser.parse(options,args,false);
            Option[] optionsArray = cmdLine.getOptions();
            String command;
            Option cmd;
            if (optionsArray.length != 0) {
                cmd = optionsArray[0];
                command = optionsArray[0].getOpt();
                switch(command) {
                    case("f1"): {
                        facade.one(cmd.getValue());
                        break;
                    }
                    case("f2"): {
                        if(cmd.getValues().length < 2)
                            throw new IllegalArgumentException("Użyto niewystarczającej liczby argumentów.");
                        facade.two(cmd.getValue(),cmd.getValue(1),cmd.getValue(2));
                        break;
                    }
                    case("f3"): {
                        if(cmd.getValues().length < 4)
                            throw new IllegalArgumentException("Użyto niewystarczającej liczby argumentów.");
                        facade.three(cmd.getValue(),cmd.getValue(1),cmd.getValue(2),cmd.getValue(3));
                        break;
                    }
                    case("f4"): {
                        String[] values = cmd.getValues();
                        int length = values.length;
                        if(length < 2)
                            throw new IllegalArgumentException("Użyto niewystarczającej liczby argumentów.");
                        String[] names = new String[length-1];
                        for(int i=1; i<length; i++)
                            names[i-1] = cmd.getValues()[i];
                        facade.four(names,cmd.getValue(0));
                        break;
                    }
                    case("f5"): {
                        facade.five(cmd.getValue());
                        break;
                    }
                    case("f6"): {
                        if(cmd.getValues().length < 3)
                            throw new IllegalArgumentException("Użyto niewystarczającej liczby argumentów.");
                        facade.six(cmd.getValue(),cmd.getValue(1), Integer.parseInt(cmd.getValue(2)));
                        break;
                    }
                    case("f7"): {
                        facade.seven(cmd.getValue());
                        break;
                    }
                    case("f8"): {
                        String[] values = cmd.getValues();
                        int length = values.length;
                        if(length < 4)
                            throw new IllegalArgumentException("Użyto niewystarczającej liczby argumentów.");
                        String[] names = new String[length-3];
                        for(int i=3; i<length; i++)
                            names[i-3] = cmd.getValues()[i];
                        facade.eight(names,cmd.getValue(),cmd.getValue(1),cmd.getValue(2));
                        break;
                    }
                    default: {
                        throw new ParseException("Użyto nieprawidłowej opcji. Dostępne opcje to " + options.getOptions());
                    }
                }
            }
            else
                formatter.printHelp("Pomoc", options);
        }
        catch(IllegalArgumentException e){
            e.getMessage();
            e.printStackTrace();
        }
        catch(ParseException e){
            System.out.println(e.getMessage());
        }
    }

}
