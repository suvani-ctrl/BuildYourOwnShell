import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static String pathValue;
    private static String[] myPath;
    static{
        String pS = File.pathSeparator;
        String p = System.getenv("PATH");
        myPath = p.split(pS);
}

private static String getFromPath(String typeCheck) {
                   for(String indivDir: myPath){
                      File f = new File(indivDir,typeCheck);
                      if( f.exists() && f.canExecute()){
                      return f.getAbsolutePath();
                      }
                      }
                    return null;
                } 
                
public static ArrayList<String> Tokenize(String input ){
StringBuilder sb = new StringBuilder();
ArrayList<String> checkInput = new ArrayList();
String checkQuotes = "";
for(int i=0; i<input.length(); i++){
    System.out.println("The checkquote right now is:"+checkQuotes);
    char inspect = input.charAt(i);

    if((inspect == '\'' || inspect == '\"') && (checkQuotes.isEmpty() || String.valueOf(inspect).equals(checkQuotes))){
        if(checkQuotes.isEmpty()){
            checkQuotes = String.valueOf(inspect);
        }
                  else if(String.valueOf(inspect).equals(checkQuotes))
                    {
                        checkQuotes = "";
                    }

            }else if(inspect == ' ' && checkQuotes.isEmpty()){
                    if(sb.length() > 0 ){
                    checkInput.add(sb.toString());
                    sb.setLength(0);
                    }
            }
            else if(inspect == '$' && (checkQuotes.equals("\"") || checkQuotes.equals(""))){
                StringBuilder sb2 = new StringBuilder();                                                  
                i++;           

                while(i < input.length() && input.charAt(i) != ' ' && input.charAt(i) != '\"' && input.charAt(i) != '$'){ 
                        char currentChar  = input.charAt(i);
                        sb2.append(currentChar);
                        i++;
                }

                i--;

                String newVal = System.getenv(sb2.toString());


                if(newVal == null ){  
                    sb.append("");
                }else{
                sb.append(newVal);    
                // System.out.println(newVal);          
                }
                }
            // else if (checkQuotes.isEmpty() && () ) {
                
            // }
           else{
        sb.append(inspect);
    }
        }
        if(sb.length() > 0){
            checkInput.add(sb.toString());
            sb.setLength(0);
            }
 
        return checkInput;
    }


      public static void main(String[] args) throws Exception {

        String input;
        Scanner sc = new Scanner(System.in);

        HashSet<String> builtinCommand = new HashSet(Arrays.asList("exit","echo","type","pwd","cd"));
        //calling the tokenize method on my main
   
        boolean programRunning = true;
        Path currentWorkingDir = Path.of(System.getProperty("user.dir"));
    
        do{
        System.out.print("$ ");
        input = sc.nextLine();
        if(input.trim().isEmpty())
            {
                continue;
            } 
        ArrayList<String> checkInput = Tokenize(input);
        System.out.println("After tokenization: "+checkInput);
        System.out.println("The value to render is rest after echo in case any myst is found");
    
        char [] myst = {'>'};


        for(String s:checkInput){
            for (char c: s.toCharArray()){
                StringBuilder myRest = new StringBuilder();
                myRest.append(c);
                System.out.println("The characeter is: "+ "  " + ":"+c);
                for(int i = 0; i<=myst.length-1;i++){
                    if(c == myst[i]){
                        char foundCh = c;
                        switch(foundCh){
                            case '>' -> { 
                                System.out.println("------------------> Im inside the switchase for > so i found a > ");
                                System.out.println("The check input in my switch case is:"+checkInput);
                                System.out.println("My new sublist arrayList");
                                int size = checkInput.size();
                                System.out.println("====size is :"+size);      
                                int redirectionIndex = checkInput.indexOf(">");
                                boolean redIndex = checkInput.contains(">");
                                System.out.println("redirection index"+ redIndex);
                                System.out.println(redirectionIndex);
                                if(redirectionIndex == -1){ 
                                     redirectionIndex = checkInput.indexOf("1>");
                                     System.out.println(redirectionIndex+"===REDIRECTION INDEX"); 
                                    // if(redirectionIndex == -1){
                                    //     redirectionIndex = checkInput.indexOf("2>");
                                    // }
                                }
                                List slice1 = checkInput.subList(0, redirectionIndex);                                
                                System.out.println("====> The value at slice 1 is "+ slice1);
                                List slice2 = checkInput.subList(redirectionIndex+1, checkInput.size());
                                String content = String.join(" ",slice1);
                               String executable = slice1.get(0).toString(); // This is just "ls"
                                String execPath = getFromPath(executable);
                         
                
                              
                                
                                // if(checkEcho == 1){
                                //     System.out.println("ok");
                                // }else{
                                //     System.exit(0);
                                // }
                                System.out.println("=================The slices 1 " +slice1);
                                System.out.println("=================The next slice 2 :"+ slice2);

                                String fileName = slice2.get(0).toString();
                                System.out.println("Stringifyyy :"+ fileName);
                                System.out.println(fileName instanceof String);
                                if (execPath != null) {
    // Pass the WHOLE chunk so all your flags (-la, -t) stay attached!

                                ProcessBuilder pb = new ProcessBuilder(slice1);
                                    pb.directory(currentWorkingDir.toFile());
                                    if (redIndex) {
                                    pb.redirectOutput(new File(fileName));
                                    pb.start().waitFor();
                                    } 
                                else{
                        
                                System.out.println("The command i have is "+ checkInput.subList(0,redirectionIndex));
                                System.out.println("The index of > in my checkinput is :"+ redirectionIndex);
                                System.out.println("The sublist i have is:"+checkInput.subList(1,size-1 ));
                                System.out.println("Accessing the file from the sublist!!!");
                                List fileAccesisng = checkInput.subList(size-1,size);
                                System.out.println("File accessing would be :"+fileAccesisng);
                                //checking if the file exists or not
                                
                                //type checking True
                                File file = new File(fileName);
                                Path path = Paths.get(fileName);
                                if(file.exists() && file.isFile()){
                                    System.out.println("File does exsts");
                                    try {
                                        Files.writeString(path,content+System.lineSeparator(),StandardOpenOption.TRUNCATE_EXISTING);
                                    } catch (IOException e) {
                                        System.out.println(e);
                                    }finally{
                                        System.out.println("Done");
                                    }
                                }else{
                                    try {
                                        System.out.println("Creating a file");
                                        Files.writeString(path,content+System.lineSeparator(),StandardOpenOption.CREATE);
                                    } catch (IOException e) {
                                        System.out.println(e);
                                    } finally {
                                        System.out.println("Done");
                                    }
                                }
                                }}
                               String next = checkInput.get(0);
                               System.out.println("I can access the next half as:"+next);
                               StringBuilder sb0 = new StringBuilder();
                               sb0.append(myst[i]);
                               System.out.println("The stringbuilder is:"+ sb0);
                               System.out.println("Now i will try to split the arrayList  ");
                               String[] arr = next.split(">");
                               ArrayList<String> finalArr = new ArrayList<>();
                               finalArr.addAll(Arrays.asList(arr));
                               System.out.println("The final array in the arrayList is "+finalArr);
                               System.out.println("The first element in the arrayList is:"+finalArr.get(0));
                            //    System.out.println("The second element in the arraylist is:"+finalArr.get(1));
                            }


                            
                        }
                    }
                }
            }
        }

        String command = checkInput.get(0);
        int sizeCheckInput = checkInput.size();
        if(input.trim().isEmpty()){
            continue;
        }
            
        switch(command){
            case "exit" -> System.exit(0);
        
            case "echo" -> {
                List<String> printEcho = checkInput.subList(1, checkInput.size());
                String finalString = String.join(" ",printEcho);
                System.out.println(finalString);
                }
        
            case "type" -> {
                if(checkInput.size() > 1){
                    
                    String typeCheck = checkInput.get(1);
                    
                    if (builtinCommand.contains(typeCheck)){
                        System.out.println(typeCheck + " is a shell builtin");
                    }
                    else{
                        String path = getFromPath(typeCheck);
                        
                        if(path != null){
                            System.out.println(typeCheck+ " is " + path);
                        }
                        else{
                            System.out.println(typeCheck + ": not found");
                        }
                        
                    }
                }   }

            case "pwd" -> System.out.println(currentWorkingDir);
            
            case "cd" -> {
                if (checkInput.size() < 2){
                    String home = System.getenv("HOME");
                    if(home == null){
                        home = System.getProperty("user.dir");
                    }
                    currentWorkingDir = Path.of(home);
                    break;
                }
                String targetPath = checkInput.get(1);
                if(targetPath.startsWith("~")){
                    targetPath = targetPath.replace("~",System.getenv("HOME"));
                }
                Path newPath = currentWorkingDir.resolve(targetPath).normalize();
                if(Files.isDirectory(newPath)){
                    currentWorkingDir = newPath;
                }
                else{
                    System.out.println("cd: "+ targetPath + ": No such file or directory");
                }
                }

            default -> {
                String execPath = getFromPath(command);
                if(execPath != null){
                    ProcessBuilder pb = new ProcessBuilder(checkInput);
                    pb.directory(currentWorkingDir.toFile());
                    pb.inheritIO().start().waitFor();
                }else{ 
                    System.out.println(command + ": command not found");
                }
                }
        }
    }
    while(programRunning);
    }

      }