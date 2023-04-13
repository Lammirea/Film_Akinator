import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class StartWindow extends JFrame{
    private JButton LoadBDBtn;
    private JButton StartGameBtn;
    private JPanel BtnPanel;
    private JPanel bcgPanel;
    private JPanel MainPanel;
    private JLabel backgroundLabel;

    final JFileChooser fc = new JFileChooser();

    public List<String> listOfStrings = new ArrayList<String>();

    public StartWindow(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(MainPanel);
        this.setSize(1080,600);
        this.setLocationRelativeTo(null);
        this.pack();

        GridLayout experimentLayout = new GridLayout(2,1,3,3);
        MainPanel.setLayout(experimentLayout);
        MainPanel.setBackground(new Color(108,110,160));

        BtnPanel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
        BtnPanel.setOpaque(true);
        BtnPanel.setBackground(new Color(108,110,160));


        //Считываем текущую директорию
        String userDirLocation = System.getProperty("user.dir");

        //Загружаем файл с устройства
        LoadBDBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == LoadBDBtn) {
                    JFileChooser jF = new JFileChooser(userDirLocation); //Открываем текущую директорию
                    jF.setDialogTitle("Открыть файл");
                    jF.setFileFilter(new FileTypeFilter(".txt", "Text File"));
                    int result = jF.showOpenDialog(null);
                    if(result == JFileChooser.APPROVE_OPTION){
                        File fi = jF.getSelectedFile();
                        try (BufferedReader br = new BufferedReader(new FileReader(fi.getPath()))) {
                            while (br.ready()) {
                                String line = br.readLine();
                                listOfStrings.add(line);
                            }
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });

        StartGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(listOfStrings.size() != 0) {
                    GameWindow GWList = new GameWindow(listOfStrings);
                    //GameWindow GW= new GameWindow("Film Chooser"); // конструктор со строкой
                    GWList.QuestionLabel.setText("База данных загружена. Для начала игры нажмите 'Нет'");
                    GWList.setSize(1080,600);
                    GWList.setVisible(true);
                    dispose();
                }else{

                    JOptionPane.showMessageDialog(MainPanel,"Сначала выберите базу знаний!","Inane error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
