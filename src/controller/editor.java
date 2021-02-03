package controller;//https://www.geeksforgeeks.org/java-swing-create-a-simple-text-editor/
import antlr.ClypsLexer;
import antlr.ClypsParser;
import execution.ExecutionManager;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.metal.*;
import javax.swing.text.Element;

public class editor extends JFrame implements ActionListener {

    JTextArea t, console, lines;
    JFrame f;
    JScrollPane jsp;

    PrintStream standardOut;

    public static ArrayList<String> errors;

    editor()
    {
        f = new JFrame("Intel Clyps Editor");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        }
        catch (Exception e) {
        }

        t = new JTextArea();
        lines = new JTextArea("1");
        lines.setBackground(Color.LIGHT_GRAY);
        lines.setEditable(false);
        t.getDocument().addDocumentListener(new DocumentListener() {
            public String getText() {
                int caretPosition = t.getDocument().getLength();
                Element root = t.getDocument().getDefaultRootElement();
                String text = "1" + System.getProperty("line.separator");
                for(int i = 2; i < root.getElementIndex(caretPosition) + 2; i++) {
                    text += i + System.getProperty("line.separator");
                }
                return text;
            }
            @Override
            public void changedUpdate(DocumentEvent de) {
                lines.setText(getText());
            }
            @Override
            public void insertUpdate(DocumentEvent de) {
                lines.setText(getText());
            }
            @Override
            public void removeUpdate(DocumentEvent de) {
                lines.setText(getText());
            }
        });

        JMenuBar mb = new JMenuBar();

        JMenu m1 = new JMenu("File");

        JMenuItem mi1 = new JMenuItem("New");
        JMenuItem mi2 = new JMenuItem("Open");
        JMenuItem mi3 = new JMenuItem("Save");
        JMenuItem mi9 = new JMenuItem("Print");
        JMenuItem mi4 = new JMenuItem("Close");

        mi1.addActionListener(this);
        mi2.addActionListener(this);
        mi3.addActionListener(this);
        mi9.addActionListener(this);
        mi4.addActionListener(this);

        m1.add(mi1);
        m1.add(mi2);
        m1.add(mi3);
        m1.add(mi9);
        m1.add(mi4);

        JMenu m2 = new JMenu("Process");

        JMenuItem mp1 = new JMenuItem("Build");
        JMenuItem mp2 = new JMenuItem("Run");

        mp1.addActionListener(this);
        mp2.addActionListener(this);

        m2.add(mp1);
        m2.add(mp2);

        mb.add(m1);
        mb.add(m2);

        console = new JTextArea();
        console.setEditable(false);
        PrintStream printStream = new PrintStream(new CustomOutputStream(console));

        // keeps reference of standard output stream
        standardOut = System.out;

        // re-assigns standard output stream and error output stream
        System.setOut(printStream);
        System.setErr(printStream);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 0, 10);

        JLabel labelEditor = new JLabel("Editor");

        panel.add(labelEditor, constraints);

        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 2.0;
        constraints.insets = new Insets(10, 10, 10, 10);

        jsp = new JScrollPane();
        jsp.getViewport().add(t);
        jsp.setRowHeaderView(lines);

        panel.add(jsp, constraints);

        constraints.gridy = 2;
        constraints.gridwidth = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(0, 10, 0, 10);

        JLabel labelConsole = new JLabel("Console");

        panel.add(labelConsole, constraints);

        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.insets = new Insets(10, 10, 10, 10);

        panel.add(new JScrollPane(console), constraints);

        f.setJMenuBar(mb);
        f.add(panel);
        f.setSize(900, 600);
        f.show();
    }

    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();

            if (s.equals("Save")) {
            JFileChooser j = new JFileChooser("f:");

            int r = j.showSaveDialog(null);

            if (r == JFileChooser.APPROVE_OPTION) {
                File fi = new File(j.getSelectedFile().getAbsolutePath());

                try {
                    FileWriter wr = new FileWriter(fi, false);

                    BufferedWriter w = new BufferedWriter(wr);

                    w.write(t.getText());
                    w.flush();
                    w.close();
                }
                catch (Exception evt) {
                    JOptionPane.showMessageDialog(f, evt.getMessage());
                }
            }
            else
                JOptionPane.showMessageDialog(f, "the user cancelled the operation");
        }
        else if (s.equals("Open")) {
            JFileChooser j = new JFileChooser("f:");

            int r = j.showOpenDialog(null);

            if (r == JFileChooser.APPROVE_OPTION) {
                File fi = new File(j.getSelectedFile().getAbsolutePath());

                try {
                    String s1 = "", sl = "";

                    FileReader fr = new FileReader(fi);
                    BufferedReader br = new BufferedReader(fr);

                    sl = br.readLine();

                    while ((s1 = br.readLine()) != null) {
                        sl = sl + "\n" + s1;
                    }

                    t.setText(sl);
                }
                catch (Exception evt) {
                    JOptionPane.showMessageDialog(f, evt.getMessage());
                }
            }
            else
                JOptionPane.showMessageDialog(f, "the user cancelled the operation");
        }
        else if (s.equals("New")) {
            t.setText("");
        }
        else if (s.equals("Close")) {
            f.dispose();
        }else if(s.equals("Build")) {
            console.setText("");
                reset();
                buildOnly(t.getText());
        }else if(s.equals("Run")){
            console.setText("");
                reset();
            perform(t.getText());
        }
    }

    private static void reset(){
        errors.clear();
        SymbolTableManager.reset();
        ExecutionManager.reset();
        StatementController.reset();
    }

    private static boolean build(){
        if (!errors.isEmpty()){
            for(String error:errors){
                System.err.println(error);
            }
            return false;
        }

        return true;
    }

    private static void buildOnly(String code){

        try {
            InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
            ClypsLexer lexer = new ClypsLexer(CharStreams.fromStream(stream, StandardCharsets.UTF_8));
            ClypsParser parser = new ClypsParser(new CommonTokenStream(lexer));

            parser.removeErrorListeners();
            parser.addErrorListener(new ClypseCustomErrorListener());
            parser.addParseListener(new ClypsCustomListener());
            ParseTree tree = parser.normalClassDeclaration();
            ClypsCustomVisitor visit = new ClypsCustomVisitor();
            visit.visit(tree);

            if (build()){
                System.err.println("No errors detected.");
            }else {
                System.err.println("Error Detected.");
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    private static void perform(String code){
            try {
                InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
                ClypsLexer lexer = new ClypsLexer(CharStreams.fromStream(stream, StandardCharsets.UTF_8));
                ClypsParser parser = new ClypsParser(new CommonTokenStream(lexer));

                parser.removeErrorListeners();
                parser.addErrorListener(new ClypseCustomErrorListener());
                parser.addParseListener(new ClypsCustomListener());
                ParseTree tree = parser.normalClassDeclaration();
                ClypsCustomVisitor visit = new ClypsCustomVisitor();
                visit.visit(tree);

                if (!errors.isEmpty()) {
                    for (String error : errors) {
                        System.err.println(error);
                    }
                }else {
                    //SymbolTableManager.reset();
                    System.out.println("EXECUTING COMMANDS");
                    ExecutionManager.getInstance().executeAllActions();
                    build();
                }

            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public static void addError(String error){
        errors.add(error);
    }

    public static void addCustomError(String error, int line){
        errors.add("Semantic Error on Line "+line+": "+error);
    }

    public static void main(String args[])
    {
        editor e = new editor();
        errors=new ArrayList<>();
        SymbolTableManager.initialize();
        ExecutionManager.initialize();
        StatementController.initialize();
    }

    public static String getInput() {
        JFrame f = new JFrame("Input");
        return JOptionPane.showInputDialog(f, "Enter Input");
    }
}