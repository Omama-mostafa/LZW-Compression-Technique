package LZW;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class Compression
{
	private JFrame frame1;
	private JFrame frame2;
	private JFrame frame3;
	static ArrayList<String> Dic = new ArrayList<String>();
	static ArrayList<String> Ori_Data = new ArrayList<String>();
	public static void main(String[] args)
	{

		for (int i = 0; i < 128; i++)
		{
			Dic.add(Character.toString((char) i));
		}
		try
		{
			File file1 = new File("Symbols.txt");
			File file2 = new File("Tags.txt");
			if (!file1.exists())
				file1.createNewFile();
			if(!file2.exists())
				file2.createNewFile();
			BufferedReader br = new BufferedReader(new FileReader("Symbols.txt"));
			BufferedWriter bw = new BufferedWriter((new FileWriter("Tags.txt")));

			///Compression

			String line;
			String str = null;
			ArrayList<Integer> CompData = new ArrayList<Integer>();
			boolean NotFound = false;
			while ((line = br.readLine()) != null)
			{
				System.out.println("Symbols : " + line);
				for (int i = 0; i < line.length();)
				{
					str = line.charAt(i) + "";
					while (Dic.contains(str) && i != line.length() - 1)
					{
						str += line.charAt(i + 1) + "";
						i++;
					}
					if (i == line.length() - 1)
					{
						if (Dic.contains(str))
							CompData.add(Dic.indexOf(str));
						else
						{
							Dic.add(str);
							CompData.add(Dic.indexOf(str.substring(0 , str.length() - 1)));
							CompData.add(Dic.indexOf(str.substring(str.length()-1 , str.length())));
							NotFound = true;
						}
						break;
					}
					Dic.add(str);
					CompData.add(Dic.indexOf(str.substring(0, str.length() - 1)));
				}
			}

			System.out.println("Compressed Data : ");
			for(int i=0; i<CompData.size(); i++)
			{
				System.out.print(CompData.get(i) + "  ");
				bw.write(CompData.get(i) + " ");
			}

			System.out.println();
			System.out.println("\n" + "Dic: ");
			for (int i = 128; i < Dic.size(); i++)
				System.out.println(i + "   " + Dic.get(i));

			///De-Compression

			ArrayList<String> DeComp_Dic = new ArrayList<String>();
			for (int i = 0; i < 128; i++)
			{
				DeComp_Dic.add(Character.toString((char) i));
			}

			for(int i=1; i<CompData.size(); i++)
			{
				String prev = null , Cur = null;
				prev = DeComp_Dic.get(CompData.get(i-1));
				if(CompData.get(i) >= 0 && CompData.get(i) < DeComp_Dic.size())
				{
					Cur = DeComp_Dic.get(CompData.get(i)).charAt(0) + "";
				}
				else
				{
					Cur = DeComp_Dic.get(CompData.get(i-1)).charAt(0) + "";
				}
				Ori_Data.add(prev);
				DeComp_Dic.add(prev + Cur);
				if(i == CompData.size()-1)
				{
					if(NotFound == true)
					{
						//DeComp_Dic.add(str);
						Ori_Data.add(DeComp_Dic.get(DeComp_Dic.size() - 1));
					}
					else
					{
						Ori_Data.add(DeComp_Dic.get(CompData.get(i)));
					}
					break;
				}
			}

			System.out.println("\n" + "Original Data : ");
			for (int i=0; i<Ori_Data.size(); i++)
				System.out.print(Ori_Data.get(i));

			System.out.println("\n");
			System.out.println("DeComp Dic: ");
			for(int i=128; i<DeComp_Dic.size();i++)
				System.out.println(i + "   " + DeComp_Dic.get(i));

			br.close();
			bw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Compression window = new Compression();
					window.frame1.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public Compression()
	{
		initialize_Form1();
		initialize_Form2();
		initialize_Form3();
	}

	private void initialize_Form1()
	{
		frame1 = new JFrame();
		frame1.setBounds(100, 100, 600, 600);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.getContentPane().setLayout(null);

		JLabel lblWel = new JLabel("Welcome ^^");
		lblWel.setBounds(10, 10, 100, 50);
		frame1.getContentPane().add(lblWel);

		JLabel lblName = new JLabel("Click on Your Choice.");
		lblName.setBounds(65, 100, 200, 50);
		frame1.getContentPane().add(lblName);

		JButton btnSubmit1 = new JButton("Compression");
		btnSubmit1.setBackground(Color.WHITE);
		btnSubmit1.setForeground(Color.RED);
		btnSubmit1.setBounds(65, 170, 150, 40);
		frame1.getContentPane().add(btnSubmit1);

		JButton btnSubmit2 = new JButton("De-compression");
		btnSubmit2.setBackground(Color.WHITE);
		btnSubmit2.setForeground(Color.RED);
		btnSubmit2.setBounds(350, 170, 150, 40);
		frame1.getContentPane().add(btnSubmit2);

		btnSubmit1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frame1.setVisible(false);
				frame2.setVisible(true);
			}
		});
		btnSubmit2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frame1.setVisible(false);
				frame3.setVisible(true);
			}
		});
	}

	private void initialize_Form2()
	{
		frame2 = new JFrame();
		frame2.setBounds(100, 100, 600, 600);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.getContentPane().setLayout(null);


		JLabel lblWel = new JLabel("Compression Selected");
		lblWel.setBounds(90, 1, 180, 20);
		frame2.getContentPane().add(lblWel);

		JLabel lblDic = new JLabel("Get Your Data : ");
		lblDic.setBounds(5, 50, 100, 20);
		frame2.getContentPane().add(lblDic);

		JButton btnSubmit = new JButton("Get Data");
		btnSubmit.setBackground(Color.WHITE);
		btnSubmit.setForeground(Color.BLUE);
		btnSubmit.setBounds(180, 50, 100, 30);
		frame2.getContentPane().add(btnSubmit);

		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(180, 100, 300, 80);
		frame2.getContentPane().add(textArea_1);
		textArea_1.setColumns(20);

		JButton btnSubmit1 = new JButton("Compress");
		btnSubmit1.setBackground(Color.WHITE);
		btnSubmit1.setForeground(Color.BLUE);
		btnSubmit1.setBounds(180, 200, 100, 30);
		frame2.getContentPane().add(btnSubmit1);

		JLabel lblTags = new JLabel("Dictionary:");
		lblTags.setBounds(5, 250, 100, 60);
		frame2.getContentPane().add(lblTags);

		JTextArea textArea_2 = new JTextArea();
		textArea_2.setBounds(180, 270, 300, 250);
		frame2.getContentPane().add(textArea_2);
		textArea_2.setColumns(20);

		btnSubmit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(null);
				File f = chooser.getSelectedFile();
				String FileName = f.getAbsolutePath();
				try
				{
					BufferedReader br = new BufferedReader(new FileReader("Symbols.txt"));
					textArea_1.read(br , null);
					br.close();
					textArea_1.requestFocus();
				}
				catch (Exception e1)
				{
					JOptionPane.showMessageDialog(null , e);
				}
			}
		});
		btnSubmit1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					for (int i = 0; i < Dic.size(); i++)
					{
						BufferedReader br = null;
						br = new BufferedReader(new FileReader("Tags.txt"));
						textArea_2.read(br , null);
						br.close();
						textArea_2.requestFocus();
					}
				}
				catch (Exception e1)
				{
					JOptionPane.showMessageDialog(null , e1);
					e1.printStackTrace();
				}
			}
		});

		JButton btnSubmit3 = new JButton("UP");
		btnSubmit3.setBackground(Color.WHITE);
		btnSubmit3.setForeground(Color.BLACK);
		btnSubmit3.setBounds(1, 1, 70, 20);
		frame2.getContentPane().add(btnSubmit3);


		btnSubmit3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frame2.setVisible(false);
				frame1.setVisible(true);
			}
		});

	}

	private void initialize_Form3()
	{
		frame3 = new JFrame();
		frame3.setBounds(100, 100, 600, 600);
		frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame3.getContentPane().setLayout(null);

		JLabel lblWel = new JLabel("De-compression Selected");
		lblWel.setBounds(90, 1, 150, 20);
		frame3.getContentPane().add(lblWel);

		JLabel lblget = new JLabel("Get your Dic : ");
		lblget.setBounds(1, 50, 150, 20);
		frame3.getContentPane().add(lblget);

		JButton btnSubmit = new JButton("Get Dic");
		btnSubmit.setBackground(Color.WHITE);
		btnSubmit.setForeground(Color.BLUE);
		btnSubmit.setBounds(180, 50, 150, 30);
		frame3.getContentPane().add(btnSubmit);

		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(200, 100, 250, 150);
		frame3.getContentPane().add(textArea_1);
		textArea_1.setColumns(20);

		JLabel lblDecomp = new JLabel("De-compress For previous Dic:");
		lblDecomp.setBounds(1, 350, 250, 50);
		frame3.getContentPane().add(lblDecomp);

		JButton btnSubmit1 = new JButton("Decompress");
		btnSubmit1.setBackground(Color.WHITE);
		btnSubmit1.setForeground(Color.BLUE);
		btnSubmit1.setBounds(180, 300, 150, 30);
		frame3.getContentPane().add(btnSubmit1);

		JTextArea textArea_2 = new JTextArea();
		textArea_2.setBounds(200, 350, 250, 120);
		frame3.getContentPane().add(textArea_2);
		textArea_2.setColumns(20);

		btnSubmit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(null);
				File f = chooser.getSelectedFile();
				String FileName = f.getAbsolutePath();
				try
				{
					BufferedReader br = new BufferedReader(new FileReader("Tags.txt"));
					textArea_1.read(br , null);
					br.close();
					textArea_1.requestFocus();
				}
				catch (Exception e1)
				{
					JOptionPane.showMessageDialog(null , e);
				}
			}
		});

		btnSubmit1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				for(int i=0; i<Ori_Data.size(); i++)
					textArea_2.append(Ori_Data.get(i));
			}
		});


		JButton btnSubmit3 = new JButton("UP");
		btnSubmit3.setBackground(Color.WHITE);
		btnSubmit3.setForeground(Color.BLACK);
		btnSubmit3.setBounds(1, 1, 70, 20);
		frame3.getContentPane().add(btnSubmit3);

		btnSubmit3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frame3.setVisible(false);
				frame1.setVisible(true);
			}
		});
	}
}
