package funProjects.RandomPickerApp;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class RandomPickerApp extends JFrame {
    private List<String> studentList;
    private List<JTextField> nameFields;
    private Random random;

    public RandomPickerApp() {
        setTitle("随机点名器");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        studentList = new ArrayList<>();
        nameFields = new ArrayList<>();
        random = new Random();

        loadStudentNames();

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new GridLayout(10, 8));

        for (String student : studentList) {
            JTextField nameField = new JTextField(student);
            nameField.setEditable(false);
            namePanel.add(nameField);
            nameFields.add(nameField);
        }

        JButton button = new JButton("开始点名");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pickRandomStudent();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(button, BorderLayout.CENTER);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(namePanel, BorderLayout.CENTER);
        container.add(panel, BorderLayout.SOUTH);
    }

    private void loadStudentNames() {
        try (BufferedReader reader = new BufferedReader(new FileReader("student_names.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                studentList.add(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "无法读取学生姓名文件: " + e.getMessage());
        }
    }

    private void pickRandomStudent() {
        if (studentList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "学生姓名列表为空");
            return;
        }

        resetNameFields();

        int randomIndex = random.nextInt(studentList.size());
        String randomStudent = studentList.get(randomIndex);

        int selectedFieldIndex = random.nextInt(nameFields.size());
        JTextField selectedField = nameFields.get(selectedFieldIndex);
        selectedField.setText(randomStudent);
        selectedField.setBackground(Color.GREEN);
    }

    private void resetNameFields() {
        for (JTextField field : nameFields) {
            field.setText("");
            field.setBackground(Color.WHITE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                RandomPickerApp app = new RandomPickerApp();
                app.setVisible(true);
            }
        });
    }
}
