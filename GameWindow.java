import javax.swing.*;
import javax.swing.event.AncestorListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class GameWindow extends JFrame{
    private JPanel MainPanel;
    private JPanel QuestionPanel;
    private JPanel BtnPanel;
    //public JLabel QuestionLabel;
    private JButton YesBtn;
    private JButton NoBtn;
    public JTextPane QuestionPane;

    // menubar
    public static JMenuBar mb;

    // JMenu
    static JMenu x;

    // Menu items
    static JMenuItem backToStart, StartFromBeginning;

    public List<String> listOfFilms = new ArrayList<String>(),
            listOfQuestions = new ArrayList<String>();

    public List<Integer> listOfChoosenItems = new ArrayList<Integer>();

    public  int j = 0;

    public GameWindow()
    {

    }
    public GameWindow(List<String> list){
        super("Film Chooser");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(MainPanel);
        this.setSize(1080,700);
        this.setLocationRelativeTo(null);
        this.pack();


        QuestionPane.setText("База данных загружена. Для начала игры нажмите 'Нет'");
        GridLayout experimentLayout = new GridLayout(2,1,3,3);
        MainPanel.setLayout(experimentLayout);

        // create a menubar
        mb = new JMenuBar();

        // create a menu
        x = new JMenu("Меню");

        // create menuitems
        backToStart = new JMenuItem("Вернуться на главный экран");
        StartFromBeginning = new JMenuItem("Начать сначала");

        //Добавление прослушки в пункты меню
        //backToStart.addActionListener(this::actionPerformed);
        //StartFromBeginning.addActionListener(this::actionPerformed);

        // add menu items to menu
        x.add(backToStart);
        x.add(StartFromBeginning);

        // add menu to menu bar
        mb.add(x);


        QuestionPane.setSize(1080,500);
        QuestionPane.setEditable(false);

        //центрируем текст в поле
        StyledDocument doc = QuestionPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        StyleConstants.setForeground(center, new Color(43,52,103));

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
                    QuestionPane.setText(String.valueOf(listOfQuestions.get(j++)));
                }
                else{ //Если данных больше нет,кнопки становятся неактивными
                    YesBtn.setEnabled(false);
                    NoBtn.setEnabled(false);
                    if (ShowResults(listOfChoosenItems).size() > 0) {
                        //for (String label : ShowResults(listOfChoosenItems)){
                            QuestionPane.setText("Советуем посмотреть: " + ShowResults(listOfChoosenItems).toString());
                        //}

                    }
                    else{
                        QuestionPane.setText("К сожалению,мы ничего не можем Вам посоветовать. Попробуйте заново пройти тест.");
                    }
                }

            }
        });


        NoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (j<listOfQuestions.size()+1) {
                    QuestionPane.setText(String.valueOf(listOfQuestions.get(j++)));
                }
                else{ //Если данных больше нет,кнопки становятся неактивными
                    NoBtn.setEnabled(false);
                    YesBtn.setEnabled(false);
                    if (ShowResults(listOfChoosenItems).size() > 0) {
                        QuestionPane.setText("Советуем посмотреть: " + ShowResults(listOfChoosenItems).toString());
                    }
                    else{
                        QuestionPane.setText("К сожалению,мы ничего не можем Вам посоветовать. Попробуйте заново пройти тест.");
                    }
                }
            }
        });

        backToStart.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame SrtWndw = new StartWindow("Film Chooser");
                SrtWndw.setSize(1080,600);
                SrtWndw.setVisible(true);
                GameWindow.this.dispose();
                GameWindow.this.setVisible(false); //не закрывает побочное окно
            }

        });

        StartFromBeginning.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listOfFilms.clear();
                listOfChoosenItems.clear();
                listOfQuestions.clear();

                MutableAttributeSet mas = QuestionPane.getInputAttributes();
                mas.removeAttributes(mas);

                QuestionPane.setText("База данных загружена. Для начала игры нажмите 'Нет'");

                NoBtn.setEnabled(true);
                YesBtn.setEnabled(true);
            }
        });
    }

    //Функция,которая отображает результат теста:
    public List<String> ShowResults(List<Integer> items){
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
        List<String> res = new ArrayList<>();

        for (Map.Entry<Integer,List<Integer>> e : mapIndex.entrySet()) {
            Collection<Integer> similar = new HashSet<Integer>(e.getValue());

            similar.retainAll(items);
            if (similar.size()>4){
                int indx = e.getKey();
                res.add(mapFilm.get(indx));
            }
        }

        return res;
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if(e.getSource() == backToStart){
//            setVisible(false);
//            JFrame DefaultWnd = new StartWindow("Film Chooser");
//            DefaultWnd.setSize(1080,600);
//            DefaultWnd.setVisible(true);
//            //GameWindow GW = new GameWindow();
//            //GW.dispose();
//        }
//        else if(e.getSource() == StartFromBeginning){
//            listOfFilms.clear();
//            listOfChoosenItems.clear();
//            listOfQuestions.clear();
//
//            MutableAttributeSet mas = QuestionPane.getInputAttributes();
//            mas.removeAttributes(mas);
//
//            QuestionPane.setText("База данных загружена. Для начала игры нажмите 'Нет'");
//
//            NoBtn.setEnabled(true);
//            YesBtn.setEnabled(true);
//        }
//    }
}
