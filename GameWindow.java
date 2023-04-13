import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class GameWindow extends JFrame{
    private JPanel MainPanel;
    private JPanel QuestionPanel;
    private JPanel BtnPanel;
    public JLabel QuestionLabel;
    private JButton YesBtn;
    private JButton NoBtn;

    public List<String> listOfFilms = new ArrayList<String>(),
            listOfQuestions = new ArrayList<String>();

    public List<Integer> listOfChoosenItems = new ArrayList<Integer>();


    public  int j = 0;
    public GameWindow(List<String> list){
        super("Film Chooser");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(MainPanel);
        this.setSize(1080,600);
        this.setLocationRelativeTo(null);
        this.pack();

        GridLayout experimentLayout = new GridLayout(2,1,3,3);
        MainPanel.setLayout(experimentLayout);

        QuestionLabel.setSize(1080,500);
        BtnPanel.setSize(1080,100);
        BtnPanel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
        BtnPanel.setOpaque(true);

        //разделение массива данных на вопросы и фильмы
        for (String line : list){
            if(line.indexOf(":") != -1){
                listOfFilms.add(line);
            }
            else{
                listOfQuestions.add(line);
            }
        }

        YesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (j<listOfQuestions.size()+1) {
                    listOfChoosenItems.add(j);
                    QuestionLabel.setText(String.valueOf(listOfQuestions.get(j++)));
                }
                else{ //Если данных больше нет,кнопки становятся неактивными
                    YesBtn.setEnabled(false);
                    NoBtn.setEnabled(false);
                    if (ShowResults(listOfChoosenItems).length() > 0) {
                        QuestionLabel.setText("Советуем посмотреть: " + ShowResults(listOfChoosenItems));
                    }
                    else{
                        QuestionLabel.setText("К сожалению,мы ничего не можем Вам посоветовать. Попробуйте заново пройти тест");
                    }
                }

            }
        });


        NoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (j<listOfQuestions.size()+1) {
                    QuestionLabel.setText(String.valueOf(listOfQuestions.get(j++)));
                }
                else{ //Если данных больше нет,кнопки становятся неактивными
                    NoBtn.setEnabled(false);
                    YesBtn.setEnabled(false);
                    if (ShowResults(listOfChoosenItems).length() > 0) {
                        QuestionLabel.setText("Советуем посмотреть: " + ShowResults(listOfChoosenItems));
                    }
                    else{
                        QuestionLabel.setText("К сожалению,мы ничего не можем Вам посоветовать. Попробуйте заново пройти тест");
                    }
                }
            }
        });
    }

    //Функция,которая отображает результат теста:
    public String ShowResults(List<Integer> items){
        Map<Integer,List<Integer>> mapIndex = new HashMap<>();
        Map<Integer,String> mapFilm = new HashMap<>();
        int k = 0,f = 0;
        for(String number : listOfFilms){
            //List<String> films = new ArrayList<>();   //записываем в отдельный массив названия
            List<Integer> index = new ArrayList<>(); //создаём лист,хранящий номера ответов
            //Добавляем все названия:
            if(number.indexOf(":") != -1){ //если мы нашли двоеточие
                int startIndex = number.indexOf(":"); //считываем индекс начала считывания символов
                int endIndex = number.indexOf("("); //считываем индекс конца считывания символов

                //films.add(number.substring(startIndex+1,endIndex-1));

                mapFilm.put(f,number.substring(startIndex+1,endIndex-1));
                f++;
            }
            //Добавляем все ответы,которые в результате выдадут название:
            if(number.indexOf("(") != -1){ //если мы нашли открывающую скобку
                int startIndex = number.indexOf("("); //считываем индекс открывающей скобки
                int endIndex = number.indexOf(")"); //считываем индекс закрывающей скобки

                Scanner dis=new Scanner(number.substring(startIndex+1,endIndex));
                String line;
                String[] lineVector;
                line = dis.nextLine(); //read numbers

                //separate all values by comma
                lineVector = line.split(",");

                //parsing the values to Integer
                for (int i = 0; i < lineVector.length;i++) {
                    index.add(Integer.parseInt(lineVector[i])); //добавляем номера без запятой в лист
                }
                mapIndex.put(k,index); //добавляем ответы в Map
                k++;
            }
        }
        String res = "";

        for (Map.Entry<Integer,List<Integer>> e : mapIndex.entrySet()) {
            Collection<Integer> similar = new HashSet<Integer>(e.getValue());

            similar.retainAll(items);
            if (similar.size()>3){
                int indx = e.getKey();
                res = mapFilm.get(indx);
            }
        }
//        if (similar.size()>3){
//            res = films.toString();
//        }

        return res;
    }

}
