import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class Question {
    private String questionText;
    private String[] options;
    private char correctAnswer;
    public Question(String questionText,String[] options,char correctAnswer){
        this.questionText=questionText;
        this.options=options;
        this.correctAnswer=correctAnswer;
    }
    public String getQuestionText(){
        return questionText;
    }
    public String[] getOptions(){
        return options;
    }
    public char getCorrectAnswer(){
        return correctAnswer;
    }
}
class QuizeService{
    private List<Question> questions;
    private int score;

    public QuizeService(List<Question> questions) {
        this.questions=questions;
        this.score=0;

    }
    public void startQuize(){
        Scanner sc = new Scanner(System.in);
        int questionNumber=1;
        for(Question q : questions){
            System.out.println("\nQ" + questionNumber++ + ": " + q.getQuestionText());
            String[] opts = q.getOptions();
            System.out.println("A) " + opts[0]);
            System.out.println("B) " + opts[1]);
            System.out.println("C) " + opts[2]);
            System.out.println("D) " + opts[3]);
            System.out.println("Your answer: ");
            char answer = Character.toUpperCase(sc.next().charAt(0));
            if(answer==q.getCorrectAnswer()){
                score++;
            }
        }
        System.out.println("\nQuize Completed!");
        System.out.println("You scored " + score + " out of " + questions.size());
    }
    
}
class FileService{
    public List<Question> loadQuestions(String filename) throws IOException{
        List<Question> list = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("Sample_Question_Import.csv"))){
            String line;
            while((line=br.readLine()) !=null){
                String question = line;
                String[] options=new String[4];
                for(int i=0;i<4;i++){
                    String[] questions = null;
                    questions[i]=br.readLine().substring(3);
                }
                char correct = br.readLine().charAt(0);
                list.add(new Question(question,options,correct));
            }
        }catch(IOException e){
            System.out.println("Error loading questions: " + e.getMessage());

        }
        return list;
    }
}
class Quize {
    public static void main(String[] args) {
        try {
            FileService fs = new FileService();
            List<Question> questions =
                    fs.loadQuestions("Sample_Question_Import.csv");
            if(questions.isEmpty()){
                System.out.println("No questions found. Please check the file!");
                return;
            }
            QuizeService quize = new QuizeService(questions);
            quize.startQuize();
        } catch (IOException ex) {
        }
    }
}

