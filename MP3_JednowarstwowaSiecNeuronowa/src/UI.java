import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;

public class UI {
    public UI(ArrayList<NeuralNetwork> neuralNetwork) {
        JFrame frame = new JFrame();

        JPanel mainPanel = new JPanel(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setMaximumSize(new Dimension(720,500));
        textArea.setLineWrap(true);

        JScrollPane scroll = new JScrollPane(textArea);

        JLabel jLabel = new JLabel(":: Insert Your Text ::", SwingConstants.CENTER);
        jLabel.setFont(new Font("SansSerif",Font.BOLD, 24));


        JPanel buttonPanel = new JPanel(new BorderLayout());
        JButton button = new JButton("Get label");

        button.addActionListener((ActionEvent event) -> {
            ArrayList<Data> dataList = new ArrayList<>();
            dataList.add(new Data("Unknown", textArea.getText()));
            for (Data data : dataList) {
                ArrayList<Results> results = new ArrayList<>();
                for (NeuralNetwork network : neuralNetwork) {
                    results.add(new Results(network.getType(), network.getPerceptron().evaluateTest(data)));
                }
                //Max selector
                boolean first = true;
                double max = 0;
                String resultType = "";
                for(Results result : results){
                    if(first){
                        max = result.getNet();
                        resultType = result.getType();
                        first = false;
                    }
                    if (result.getNet() > max) {
                        max = result.getNet();
                        resultType = result.getType();
                    }
                }
                System.out.println("The results:");
                System.out.println(data.getLanguage() + " -> " + resultType);
                System.out.println();
            }
        });

        buttonPanel.add(button, BorderLayout.CENTER);

        mainPanel.add(jLabel, BorderLayout.PAGE_START);
        mainPanel.add(scroll, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.PAGE_END);

        frame.add(mainPanel);


        frame.setPreferredSize(new Dimension(720, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}