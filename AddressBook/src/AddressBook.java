import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JTextField;
import javax.swing.ListModel;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
//import src.Person;
import javax.swing.JScrollBar;
import javax.swing.DefaultListModel;
/**
 * AddressBook�� ����ͨѶ¼
 * @author Yannis
 * @since jdk 1.8
 * @version 1.0
 */
public class AddressBook extends JFrame {

	private static final long serialVersionUID = 1L;
	
	 //��vector���ڴ��д洢ͨѶ¼��������ϵ�˵�����
	Vector<String> v = new Vector<String>();  
	//��Hashtable���ڴ��д洢ͨѶ¼��������name��key��Person��value
	Hashtable<String, Person> ht = new Hashtable<String, Person>();
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	int mode = 0;    //ģʽ
	private JPanel contentPane;
	private JTextField jtf_name;
	private JLabel jl_namewarn;
	private JLabel jl_birthday;
	private JTextField jtf_birth;
	private JLabel jl_phone;
	private JTextField jtf_phone;
	private JLabel jl_mail;
	private JTextField jtf_mail;
	private JLabel jl_eg;    //��ע
	private JTextField jtf_eg;
	private JButton jb_new;
	private JButton jb_change;
	private JButton jb_save;
	private JButton jb_delete;
	private JButton jb_exit;
	private JLabel jl_birthwarn;
	private JLabel jl_mailwarn;
	private JScrollBar scrollBar;
	private JList<String> list = new JList<String>();
	DefaultListModel<String> listModel ;
	/**
	 * ����
	 * @param args-String �����в���������
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddressBook frame = new AddressBook();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public AddressBook() throws ClassNotFoundException, IOException {
		setTitle("  ����ͨѶ¼");
		initUI();
		setMode(0);
	}
	/**
	 * ����ģʽ�����0���½�1���޸�2
	 * @param mode ��������ģʽ
	 */
	public void setMode(int mode) {
		this.mode = mode;
		// ���ģʽmode=0���½���ɾ�����޸İ�ť��Ч�����水ť��Ч�������ı���ֻ����
		if (mode == 0) {
			jb_new.setEnabled(true);
			jb_change.setEnabled(true);
			jb_save.setEnabled(false);
			jb_delete.setEnabled(true);
			jtf_name.setEditable(false);
			jtf_birth.setEditable(false);
			jtf_phone.setEditable(false);
			jtf_mail.setEditable(false);
			jtf_eg.setEditable(false);
			clearAll();
		}
		// ����ģʽmode=1���½���ɾ�����޸İ�ť��Ч�����水ť��Ч, �����ı�����գ�״̬�ɱ༭��
		if (mode == 1) {
			jb_new.setEnabled(false);
			jb_change.setEnabled(false);
			jb_save.setEnabled(true);
			jb_delete.setEnabled(false);
			clearAll();
			jtf_name.setEditable(true);
			jtf_birth.setEditable(true);
			jtf_phone.setEditable(true);
			jtf_mail.setEditable(true);
			jtf_eg.setEditable(true);
		}
		// �޸�ģʽmode=2���½���ɾ�����޸İ�ť��Ч�����水ť��Ч�������ı���ɱ༭���������⣩��
		if (mode == 2) {
			jb_new.setEnabled(false);
			jb_change.setEnabled(false);
			jb_save.setEnabled(true);
			jb_delete.setEnabled(false);
			jtf_name.setEditable(true);
			jtf_birth.setEditable(true);
			jtf_phone.setEditable(true);
			jtf_mail.setEditable(true);
			jtf_eg.setEditable(true);
		}
	}
	/**
	 * ��������ı���
	 */
	public void clearAll() {
		jtf_name.setText("");
		jtf_birth.setText("");
		jtf_phone.setText("");
		jtf_mail.setText("");
		jtf_eg.setText("");
	}
	/**
	 * �����л�����
	 * @throws IOException I/O �쳣
	 * @throws ClassNotFoundException ���Ҳ����쳣
	 */
	public void load() throws IOException, ClassNotFoundException{
			
		//����һ���ļ�����������
		FileInputStream fi = new FileInputStream("d:\\address.ini");
		//�ļ�������һ���������л�������
		ObjectInputStream si = new ObjectInputStream(fi);
		//ʹ�ø��������뺯�������ļ��б���Ķ����ȡ���ڴ��У���������Ӧ����
		ht = (Hashtable)si.readObject();
		v = (Vector) si.readObject();
	}
	/**
	 * ���л�����
	 * @throws IOException I/O �쳣
	 */
	public void save() throws IOException{
		
		//����һ���ļ����������
		FileOutputStream fo = new FileOutputStream("d:\\address.ini");
		
		//���ļ�������һ���������л������
		ObjectOutputStream so = new ObjectOutputStream(fo);
		so.writeObject(ht);
		so.writeObject(v);
	}
	/**
	 * �ж������Ƿ�Ϊ�գ��������ڡ��ʼ��Ƿ���ϸ�ʽ
	 * @return true-��֤�ϸ�
	 */
	public boolean verify(){
		String name =  jtf_name.getText().trim();
		String birth = jtf_birth.getText().trim();
		String birth_regex = "\\d{4}-\\d{2}-\\d{2}";
		String mail = jtf_mail.getText().trim();
		String mail_regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		if(name.equals("")){
			return false;
		}else if(birth.matches(birth_regex)==false){
			return false;
		}else if(mail.matches(mail_regex)==false){
			return false;
		}
		return true;
	}
	/**
	 * ��֤������е������Ƿ��Ѵ���
	 * @return true-������  false-������ͬ������
	 */
	public boolean nameverify(){
		int count=0;
		if(!v.isEmpty()){                //vector��Ϊ��ʱ
			for(String stri : v){               //����vector
				if(stri==(jtf_name.getText().trim())){       //����Ѵ�����ͬ��������countΪ1
					count=1;
				}
			}
		}
		if(count==0){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * ���ı����е�ֵ���õ�Person������
	 * @return Person����
	 */
	public Person getInfo(){
		String name =  jtf_name.getText().trim();
		String birth = jtf_birth.getText().trim();
		String mail = jtf_mail.getText().trim();
		String phone = jtf_phone.getText().trim();
		String memo = jtf_eg.getText().trim();
		Person p = new Person();
		p.setName(name);
		p.setBirthDate(birth);
		p.setMail(mail);
		p.setPhone(phone);
		p.setMemo(memo);
		return 	p;	
	}
	/**
	 * ��Person�������õ��ı���
	 * @param p Person�����
	 * @throws IOException I/O �쳣
	 * @throws ClassNotFoundException ���Ҳ����쳣
	 */
	public void setInfo(Person p) throws ClassNotFoundException, IOException{
		load();
		jtf_name.setText(p.getName());
		jtf_birth.setText(p.getBirthDate());
		jtf_phone.setText(p.getPhone());
		jtf_mail.setText(p.getMail());
		jtf_eg.setText(p.getMemo());
	}
	/**
	 * �˳�ǰҪ�����л�
	 * @throws IOException I/O �쳣
	 */
	public void exit() throws IOException{
		save();
		System.exit(0);
	}
	/**
	 * ���ɽ�������Լ��¼���������
	 * @throws IOException I/O �쳣
	 * @throws ClassNotFoundException ���Ҳ����쳣
	 */
	public void initUI() throws ClassNotFoundException, IOException {
		setFont(new Font("���� Light", Font.PLAIN, 12));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 646, 426);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		//label����
		JLabel jl_name = new JLabel("������");
		jl_name.setFont(new Font("���� Light", Font.BOLD, 18));
		
		jtf_name = new JTextField();
		jtf_name.setColumns(10);
		
		jl_namewarn = new JLabel("��������Ϊ�գ������ظ�������Ψһ");
		jl_namewarn.setFont(new Font("���� Light", Font.PLAIN, 14));
		
		jl_birthday = new JLabel("�������ڣ�");
		jl_birthday.setFont(new Font("���� Light", Font.BOLD, 18));
		
		jtf_birth = new JTextField();
		jtf_birth.setColumns(15);
		
		jl_phone = new JLabel("�ֻ���");
		jl_phone.setFont(new Font("���� Light", Font.BOLD, 18));
		
		jtf_phone = new JTextField();
		jtf_phone.setColumns(20);
		
		jl_mail = new JLabel("�����ʼ���");
		jl_mail.setFont(new Font("���� Light", Font.BOLD, 18));
		
		jtf_mail = new JTextField();
		jtf_mail.setColumns(20);
		
		jl_eg = new JLabel("��ע��");
		jl_eg.setFont(new Font("���� Light", Font.BOLD, 18));
		
		jtf_eg = new JTextField();
		jtf_eg.setColumns(30);
		//�½���ť
		jb_new = new JButton("��        ��");
		jb_new.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setMode(1);				
			}
		});
		jb_new.setFont(new Font("���� Light", Font.PLAIN, 19));
		
		jb_change = new JButton("��        ��");
		jb_change.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMode(2);
			}
		});
		jb_change.setFont(new Font("���� Light", Font.PLAIN, 19));
		
		jb_save = new JButton("��        ��");
		jb_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//���½�ģʽ����������Ϊ�գ��������ڡ��ֻ��������ʼ�Ҫ������Ӧ�ĸ�ʽ������ҪΨһ
				if(mode==1){					
						if(verify()==false){         //У��ɹ�
							if(nameverify()==true){       //true��ʾ�������Ѿ�����
								JOptionPane.showMessageDialog(null, "����ϵ���Ѵ��ڣ���������������");
								jtf_name.requestFocus(true);  //�û����ı����ý���
								return;
							}else{
								JOptionPane.showMessageDialog( null, "�����Ϣ��ʽ��д�������������룡");
								jtf_name.requestFocus(true);  //�û����ı����ý���
								return;
							}							
					}else{
						try {
							int n = JOptionPane.showConfirmDialog(null, "�Ƿ񱣴�", "ȷ��", JOptionPane.YES_NO_OPTION);
							if(n==JOptionPane.YES_OPTION){
								Person p = new Person();
								p=getInfo();	     //���ı����ֵ�ŵ�Person������
								ht.put(p.getName(), p);   //name��key��Person��value�����½�����ϵ�˷Ž�hashtable
								v.addElement(p.getName());   //��������ӽ�vector
								save();   //���л�
								list.setListData(v);
								setMode(0);
							}
							else{
								jtf_name.requestFocus(true);  //�û����ı����ý���
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
				//���޸�ģʽ��ֻ��������ڡ��ֻ��������ʼ�Ҫ������Ӧ��ʽ
				if(mode==2){
					if(verify()==false){
						JOptionPane.showMessageDialog( null, "�����Ϣ��ʽ��д�������������룡");
						jtf_birth.requestFocus(true);  //�û����ı����ý���
						return;
					}else{						
						try {
							load();   //�����л�
							Person p = new Person();
							p=getInfo();	  //���ı�����������person������
							ht.put(jtf_name.getText(), p);   //����hashtable
							save();     //���л�
							setMode(0);     //��Ϊ���ģʽ
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		jb_save.setFont(new Font("���� Light", Font.PLAIN, 19));
		
		jb_delete = new JButton("ɾ        ��");
		jb_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(null, "�Ƿ�ɾ��", "ȷ��", JOptionPane.YES_NO_OPTION);
				if(n==JOptionPane.YES_OPTION){	
					try {
						load();
						v.remove(jtf_name.getText().trim());
						ht.remove(jtf_name.getText().trim());						
						save();
						clearAll();
						list.setListData(v);
						setMode(0);
						
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else{
					jtf_name.requestFocus(true);  //�û����ı����ý���
				}
			}
		});
		jb_delete.setFont(new Font("���� Light", Font.PLAIN, 19));
		
		jb_exit = new JButton("��        ��");
		jb_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int n = JOptionPane.showConfirmDialog(null, "�Ƿ��˳�", "ȷ��", JOptionPane.YES_NO_OPTION);
					if(n==JOptionPane.YES_OPTION){	
					exit();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		jb_exit.setFont(new Font("���� Light", Font.PLAIN, 19));
		
		jl_birthwarn = new JLabel("���ڸ�ʽ��xxxx-xx-xx");
		jl_birthwarn.setFont(new Font("���� Light", Font.PLAIN, 15));
		
		jl_mailwarn = new JLabel("�ʼ���ʽ��xxxx@xxxx.xxx");
		jl_mailwarn.setFont(new Font("���� Light", Font.PLAIN, 15));
		
		/**
		 * �б���
		 */		
		//��Jlist������ʾ��������Ŀ�����ǡ��洢���ں���������һ���һ��ListModel�е�
		//�ڳ�����Ҫ��Jlist�е���Ŀ�����������ӡ�ɾ���Ȳ���ʱ�������ListModel����ɵ�
		listModel = new DefaultListModel<String>();
		list.setModel(listModel);			
				load();
				for(String s : v){
				//�����б���
				listModel.addElement(s);
			}
		
		list.addListSelectionListener(new ListSelectionListener() {
			//��дvalueChanged(ListSelectionEvent arg0)���������ܲ�׽JList�б���ı仯
			//ʵʱˢ�¸�����Ϣ���и����ı����ֵ
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				String a = list.getSelectedValue();  
				Person p = new Person();
				if(!ht.get(a).equals("")){
					p = ht.get(a);      //��Hashtable����key������ȡ��value��person����
				}				
				try {
					setInfo(p);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		//jl_namelist.setSelectedIndex(0);
		
		scrollBar = new JScrollBar();
		scrollBar.add(list);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(jl_name))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(22)
							.addComponent(list, GroupLayout.PREFERRED_SIZE, 360, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(jl_phone)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(jl_birthday)
										.addComponent(jl_mail)
										.addComponent(jl_eg))
									.addGap(3)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(jtf_eg, GroupLayout.PREFERRED_SIZE, 425, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(jtf_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addGap(43)
											.addComponent(jl_namewarn))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(jtf_birth, GroupLayout.PREFERRED_SIZE, 282, GroupLayout.PREFERRED_SIZE)
											.addGap(40)
											.addComponent(jl_birthwarn))
										.addComponent(jtf_phone, GroupLayout.PREFERRED_SIZE, 358, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
											.addComponent(jb_new, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(jtf_mail, GroupLayout.PREFERRED_SIZE, 282, GroupLayout.PREFERRED_SIZE)
												.addGap(44)
												.addComponent(jl_mailwarn))
											.addComponent(jb_change, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
											.addComponent(jb_save, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(jb_exit, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
												.addComponent(jb_delete, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE))))))))
					.addGap(551))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(1)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(jl_name)
						.addComponent(jtf_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jl_namewarn, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(jl_birthday)
						.addComponent(jtf_birth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jl_birthwarn))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(jl_phone)
						.addComponent(jtf_phone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(jl_mail)
						.addComponent(jtf_mail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jl_mailwarn))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(jl_eg)
						.addComponent(jtf_eg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollBar, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(jb_new)
								.addGap(9)
								.addComponent(jb_change)
								.addGap(9)
								.addComponent(jb_save)
								.addGap(8)
								.addComponent(jb_delete)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jb_exit))
							.addComponent(list, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)))
					.addGap(220))
		);
		contentPane.setLayout(gl_contentPane);
	}
}