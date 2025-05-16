package com.arcadia.editor.entities;

import javax.swing.*;
import java.awt.*;

public class Settings extends JFrame {

    private JPanel panel = new JPanel();

    private int mWidth = 800;
    private int mHeight = 600;

    private Scenario scenario;

    public Settings(){
        this(new Scenario("Example Name"));
    }

    public Settings(Scenario scenario){
        this.scenario = scenario;

        this.setTitle("Settings");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(mWidth, mHeight);
        this.setLocation(400, 200);
        this.setFocusable(true);
        this.requestFocus();

        initPanel();
        this.add(panel);
    }

    private void initPanel(){
        JLabel scenarioNameLabel = new JLabel("Name:",SwingConstants.LEFT);
        panel.add(scenarioNameLabel);
        JTextField scenarioNameTextField;
        scenarioNameTextField = new JTextField(scenario.getScenarioName(),15);
        panel.add(scenarioNameTextField);

        JButton buttonSave = new JButton("OK");
        buttonSave.addActionListener(e ->{
            String name = scenarioNameTextField.getText();
            scenario.setScenarioName(name);
        });

        panel.add(buttonSave);
    }

    public Scenario getScenario(){
        return scenario;
    }
}
