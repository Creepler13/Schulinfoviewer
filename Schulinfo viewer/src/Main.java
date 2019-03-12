import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JList;

public class Main extends JFrame {

	private String[] klassen = { "5A", "5B", "5C", "5D", "6A", "6B", "6C", "6D", "7A", "7B", "7C", "7D", "8A", "8B",
			"9A", "9B", "9C", "9D" };
	private String[] replaceses = { "br","<", ">", "td class=", "td", "tr", "/tr",  "/", "\"", "ungerade", "gerade",
			"Ver", "etung", "Klasse", "Fach", "Raum", "Anmerkung", "\\s+" };
	private String[] input2;
	private ArrayList<String> output = new ArrayList<>();
	private JPanel contentPane;
	private ArrayList<String> Lehrer = new ArrayList<>();
	private ArrayList<String> Fach = new ArrayList<>();
	private int off = 0;
	private String inputLine;
	private JTextPane textPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 */
	public Main() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		URL oracle = new URL("https://fsg-preetz.org/idesk/vertretungsplan.html");
		BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

		String input = null;

		while (!(inputLine = in.readLine()).equals("<table><tr>")) {
		}

		while ((inputLine = in.readLine()) != null) {
			if (inputLine.equals("<table><tr>")) {

			}

			input = input + inputLine;

		}

		in.close();

		for (int i = 0; i < replaceses.length; i++) {
			input = input.replaceAll(replaceses[i], " ");
		}

		input2 = input.split(" ");
		for (int j = 0; j < input2.length; j++) {

			output.add(input2[j]);
		}

		try {
			File file = new File("src/lehrer.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String b;
			while ((b = br.readLine()) != null) {
				Lehrer.add(b);
			}
		} catch (Exception e) {
		}
		try {
			File file = new File("src/fach.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String b;
			while ((b = br.readLine()) != null) {
				Fach.add(b);
			}
		} catch (Exception e) {
		}

		JComboBox klasse = new JComboBox(klassen);
		klasse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				String petName = (String) cb.getSelectedItem();
				outputer(petName, output);
			}
		});
		klasse.setBounds(60, 216, 82, 20);
		contentPane.add(klasse);

		JTextPane txtpnKlasse = new JTextPane();
		txtpnKlasse.setEditable(true);
		txtpnKlasse.setText("klasse");
		txtpnKlasse.setBounds(10, 216, 40, 20);
		contentPane.add(txtpnKlasse);

		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(10, 11, 414, 181);
		contentPane.add(textPane);
		String petName = (String) klasse.getSelectedItem();
		outputer(petName, output);

	}

	public void outputer(String a, ArrayList<String> b) {

		if (indexOfAll(a, b).size() != 0) {
			ArrayList<Integer> c = indexOfAll(a, b);
			String g = "";
			String l = "";
			String f = "";
			for (int i = 0; i < c.size(); i++) {
				f = Fach.get(Fach.indexOf(b.get(c.get(i) + 1))+ 1);
				
				g = g + " Stunde: " + b.get(c.get(i) - 1) + " Fach: " + f  + " Raum: "
						+ b.get(c.get(i) + 2) + "\n";
			}
			textPane.setText(g);
		} else {
			textPane.setText("Nichts");
		}
	}

	static <T> ArrayList<Integer> indexOfAll(T obj, ArrayList<T> list) {
		final ArrayList<Integer> indexList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			if (obj.equals(list.get(i))) {
				indexList.add(i);
			}
		}

		return indexList;
	}
}
