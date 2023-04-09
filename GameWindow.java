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
        this.setSize(950,600);
        this.setLocationRelativeTo(null);
        this.pack();

        GridLayout experimentLayout = new GridLayout(2,1,3,3);
        MainPanel.setLayout(experimentLayout);

        QuestionLabel.setSize(950,500);
        BtnPanel.setSize(950,100);
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
                if (j<listOfQuestions.size()) {
                    listOfChoosenItems.add(j);
                    QuestionLabel.setText(String.valueOf(listOfQuestions.get(j++)));
                }
                else{ //Если данных больше нет,кнопки становятся неактивными
                    YesBtn.setEnabled(false);
                    NoBtn.setEnabled(false);
                    ShowResults(listOfChoosenItems);
                }

            }
        });


        NoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (j<listOfQuestions.size()) {
                    QuestionLabel.setText(String.valueOf(listOfQuestions.get(j++)));
                }
                else{ //Если данных больше нет,кнопки становятся неактивными
                    NoBtn.setEnabled(false);
                    YesBtn.setEnabled(false);
                    ShowResults(listOfChoosenItems);
                }
            }
        });
    }

    //Функция,которая отображает результат теста:
    public void ShowResults(List<Integer> items){
        for(String number : listOfFilms){
            if(number.indexOf("(") != -1){ //если мы нашли открывающую скобку
                
            }
        }
    }
}
