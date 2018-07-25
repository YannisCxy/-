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
 * AddressBook类 个人通讯录
 * @author Yannis
 * @since jdk 1.8
 * @version 1.0
 */
public class AddressBook extends JFrame {

	private static final long serialVersionUID = 1L;
	
	 //用vector在内存中存储通讯录中所有联系人的姓名
	Vector<String> v = new Vector<String>();  
	//用Hashtable在内存中存储通讯录，用姓名name做key，Person做value
	Hashtable<String, Person> ht = new Hashtable<String, Person>();
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	int mode = 0;    //模式
	private JPanel contentPane;
	private JTextField jtf_name;
	private JLabel jl_namewarn;
	private JLabel jl_birthday;
	private JTextField jtf_birth;
	private JLabel jl_phone;
	private JTextField jtf_phone;
	private JLabel jl_mail;
	private JTextField jtf_mail;
	private JLabel jl_eg;    //备注
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
	 * 运行
	 * @param args-String 命令行参数的数组
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
		setTitle("  个人通讯录");
		initUI();
		setMode(0);
	}
	/**
	 * 设置模式：浏览0、新建1、修改2
	 * @param mode 程序运行模式
	 */
	public void setMode(int mode) {
		this.mode = mode;
		// 浏览模式mode=0：新建、删除、修改按钮有效，保存按钮无效，所有文本框只读；
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
		// 增加模式mode=1：新建、删除、修改按钮无效，保存按钮有效, 所有文本框清空，状态可编辑；
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
		// 修改模式mode=2：新建、删除、修改按钮无效，保存按钮有效，所有文本框可编辑（姓名除外）；
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
	 * 清空所有文本框
	 */
	public void clearAll() {
		jtf_name.setText("");
		jtf_birth.setText("");
		jtf_phone.setText("");
		jtf_mail.setText("");
		jtf_eg.setText("");
	}
	/**
	 * 反序列化集合
	 * @throws IOException I/O 异常
	 * @throws ClassNotFoundException 类找不到异常
	 */
	public void load() throws IOException, ClassNotFoundException{
			
		//创建一个文件输入流对象
		FileInputStream fi = new FileInputStream("d:\\address.ini");
		//文件流创建一个对象序列化输入流
		ObjectInputStream si = new ObjectInputStream(fi);
		//使用该流的输入函数，将文件中保存的对象读取到内存中，并创建相应对象。
		ht = (Hashtable)si.readObject();
		v = (Vector) si.readObject();
	}
	/**
	 * 序列化集合
	 * @throws IOException I/O 异常
	 */
	public void save() throws IOException{
		
		//创建一个文件输出流对象
		FileOutputStream fo = new FileOutputStream("d:\\address.ini");
		
		//用文件流创建一个对象序列化输出流
		ObjectOutputStream so = new ObjectOutputStream(fo);
		so.writeObject(ht);
		so.writeObject(v);
	}
	/**
	 * 判断姓名是否为空，出生日期、邮件是否符合格式
	 * @return true-验证合格
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
	 * 验证输入框中的名字是否已存在
	 * @return true-不存在  false-存在相同的名字
	 */
	public boolean nameverify(){
		int count=0;
		if(!v.isEmpty()){                //vector不为空时
			for(String stri : v){               //遍历vector
				if(stri==(jtf_name.getText().trim())){       //如果已存在相同名字则置count为1
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
	 * 将文本框中的值设置到Person对象中
	 * @return Person对象
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
	 * 将Person对象设置到文本框
	 * @param p Person类对象
	 * @throws IOException I/O 异常
	 * @throws ClassNotFoundException 类找不到异常
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
	 * 退出前要求序列化
	 * @throws IOException I/O 异常
	 */
	public void exit() throws IOException{
		save();
		System.exit(0);
	}
	/**
	 * 生成界面代码以及事件监听处理
	 * @throws IOException I/O 异常
	 * @throws ClassNotFoundException 类找不到异常
	 */
	public void initUI() throws ClassNotFoundException, IOException {
		setFont(new Font("等线 Light", Font.PLAIN, 12));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 646, 426);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		//label姓名
		JLabel jl_name = new JLabel("姓名：");
		jl_name.setFont(new Font("等线 Light", Font.BOLD, 18));
		
		jtf_name = new JTextField();
		jtf_name.setColumns(10);
		
		jl_namewarn = new JLabel("姓名不能为空，不能重复，必须唯一");
		jl_namewarn.setFont(new Font("等线 Light", Font.PLAIN, 14));
		
		jl_birthday = new JLabel("出生日期：");
		jl_birthday.setFont(new Font("等线 Light", Font.BOLD, 18));
		
		jtf_birth = new JTextField();
		jtf_birth.setColumns(15);
		
		jl_phone = new JLabel("手机：");
		jl_phone.setFont(new Font("等线 Light", Font.BOLD, 18));
		
		jtf_phone = new JTextField();
		jtf_phone.setColumns(20);
		
		jl_mail = new JLabel("电子邮件：");
		jl_mail.setFont(new Font("等线 Light", Font.BOLD, 18));
		
		jtf_mail = new JTextField();
		jtf_mail.setColumns(20);
		
		jl_eg = new JLabel("备注：");
		jl_eg.setFont(new Font("等线 Light", Font.BOLD, 18));
		
		jtf_eg = new JTextField();
		jtf_eg.setColumns(30);
		//新建按钮
		jb_new = new JButton("新        建");
		jb_new.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setMode(1);				
			}
		});
		jb_new.setFont(new Font("等线 Light", Font.PLAIN, 19));
		
		jb_change = new JButton("修        改");
		jb_change.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMode(2);
			}
		});
		jb_change.setFont(new Font("等线 Light", Font.PLAIN, 19));
		
		jb_save = new JButton("保        存");
		jb_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//在新建模式下姓名不能为空，出生日期、手机、电子邮件要符合相应的格式，姓名要唯一
				if(mode==1){					
						if(verify()==false){         //校验成功
							if(nameverify()==true){       //true表示该名字已经存在
								JOptionPane.showMessageDialog(null, "该联系人已存在，请重新输入名字");
								jtf_name.requestFocus(true);  //用户名文本框获得焦点
								return;
							}else{
								JOptionPane.showMessageDialog( null, "相关信息格式填写错误，请重新输入！");
								jtf_name.requestFocus(true);  //用户名文本框获得焦点
								return;
							}							
					}else{
						try {
							int n = JOptionPane.showConfirmDialog(null, "是否保存", "确认", JOptionPane.YES_NO_OPTION);
							if(n==JOptionPane.YES_OPTION){
								Person p = new Person();
								p=getInfo();	     //把文本框的值放到Person对象中
								ht.put(p.getName(), p);   //name做key，Person做value，把新建的联系人放进hashtable
								v.addElement(p.getName());   //把名字添加进vector
								save();   //序列化
								list.setListData(v);
								setMode(0);
							}
							else{
								jtf_name.requestFocus(true);  //用户名文本框获得焦点
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
				//在修改模式下只需出生日期、手机、电子邮件要符合相应格式
				if(mode==2){
					if(verify()==false){
						JOptionPane.showMessageDialog( null, "相关信息格式填写错误，请重新输入！");
						jtf_birth.requestFocus(true);  //用户名文本框获得焦点
						return;
					}else{						
						try {
							load();   //反序列化
							Person p = new Person();
							p=getInfo();	  //将文本框内容置于person对象中
							ht.put(jtf_name.getText(), p);   //更新hashtable
							save();     //序列化
							setMode(0);     //置为浏览模式
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
		jb_save.setFont(new Font("等线 Light", Font.PLAIN, 19));
		
		jb_delete = new JButton("删        除");
		jb_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(null, "是否删除", "确认", JOptionPane.YES_NO_OPTION);
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
					jtf_name.requestFocus(true);  //用户名文本框获得焦点
				}
			}
		});
		jb_delete.setFont(new Font("等线 Light", Font.PLAIN, 19));
		
		jb_exit = new JButton("退        出");
		jb_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int n = JOptionPane.showConfirmDialog(null, "是否退出", "确认", JOptionPane.YES_NO_OPTION);
					if(n==JOptionPane.YES_OPTION){	
					exit();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		jb_exit.setFont(new Font("等线 Light", Font.PLAIN, 19));
		
		jl_birthwarn = new JLabel("日期格式：xxxx-xx-xx");
		jl_birthwarn.setFont(new Font("等线 Light", Font.PLAIN, 15));
		
		jl_mailwarn = new JLabel("邮件格式：xxxx@xxxx.xxx");
		jl_mailwarn.setFont(new Font("等线 Light", Font.PLAIN, 15));
		
		/**
		 * 列表处理
		 */		
		//在Jlist中所显示的所有项目，都是“存储”在和它捆绑在一起的一个ListModel中的
		//在程序中要对Jlist中的项目进行诸如增加、删除等操作时是在这个ListModel中完成的
		listModel = new DefaultListModel<String>();
		list.setModel(listModel);			
				load();
				for(String s : v){
				//增加列表项
				listModel.addElement(s);
			}
		
		list.addListSelectionListener(new ListSelectionListener() {
			//重写valueChanged(ListSelectionEvent arg0)方法，才能捕捉JList列表项的变化
			//实时刷新个人信息栏中各项文本框的值
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				String a = list.getSelectedValue();  
				Person p = new Person();
				if(!ht.get(a).equals("")){
					p = ht.get(a);      //从Hashtable中用key：名字取得value：person对象
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