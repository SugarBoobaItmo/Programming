package ui.interfaces;

import javax.swing.JOptionPane;

import cli.interfaces.LineWriter;

public class OutputWriter implements LineWriter{

    @Override
    public void writeLine(String line) {
        JOptionPane.showMessageDialog(null, line);
        
    }
    
}
