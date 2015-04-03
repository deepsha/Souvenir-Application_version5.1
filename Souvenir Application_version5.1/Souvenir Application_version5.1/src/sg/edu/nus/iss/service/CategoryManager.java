package sg.edu.nus.iss.service;



import java.awt.Component;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import sg.edu.nus.iss.gui.CategoryPanel;
import sg.edu.nus.iss.gui.MainMenu;
import sg.edu.nus.iss.gui.ProductPanel;
import sg.edu.nus.iss.models.Member;
import sg.edu.nus.iss.models.Product;
import sg.edu.nus.iss.models.Category;
import sg.edu.nus.iss.util.Constants;

public class CategoryManager {
	private ArrayList<Category> categories;
	private MainMenu mainMenu;
	private CategoryPanel categoryPanel;
	private Object categoryList;
	public CategoryManager(MainMenu mainMenu) throws IOException {
		this.mainMenu = mainMenu;
		categoryPanel = new CategoryPanel(this);

	}

	public CategoryManager(){}
	public ArrayList<Category> ListCategory() {
		ArrayList<Category> ListCategory = new ArrayList<Category>();
		String filename = "./data/Category.dat";
		String data;
		try {
			BufferedReader Reader = new BufferedReader(new FileReader(filename));
			while ((data = Reader.readLine()) != null) {
				// Deal with the line
				String[] dataTmp = data.split(",");
				String categoryCode = dataTmp[0];
				String categoryName = dataTmp[1];
				Category category = new Category(categoryCode, categoryName);
				ListCategory.add(category);
			}
			Reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ListCategory;
	}
	public boolean addCategory(String categoryCode, String categoryName)throws IOException {

		boolean valid=false;
		Category p = new Category(categoryCode,categoryName);
		this.ListCategory().add(p);
		FileWriter w = new FileWriter("./data/Category.dat",true);
		PrintWriter pr = new PrintWriter(w);
		pr.write("\r"+p.toString());
		pr.close();
		valid=true;
		return valid;
	}
	public void refresh() throws IOException {
		mainMenu.refreshCategoryPanel();
		mainMenu.displayCategoryPanel();
		
	}

	public CategoryPanel getCategoryPanel() {
		return categoryPanel;
	}

public List<Category> searchDataAndDisplay(String data, String value) {
		
		ArrayList<Category> searchedlist=new ArrayList<Category>();
		ArrayList<Category> categoryList = null;
		Category searchedCategory;
		System.out.println("searchDataAndDisplay"+value+data);
		try {
			categoryList = retrieveCategoryDataFromFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      Iterator<Category> iterator = categoryList.iterator();
				while (iterator.hasNext()) {		
					searchedCategory=new Category();
					Category dis=iterator.next();
					if(data.equalsIgnoreCase("Category Code"))
					{
					
						if(value.equalsIgnoreCase(dis.getCategoryCode())){
						//searcheddiscount=new Discount();
					
							searchedCategory=dis;
							searchedlist.add(searchedCategory);	
					
						
						}
					}
					else if(data.equalsIgnoreCase("Category Name"))
					{
						if(value.equalsIgnoreCase(dis.getCategoryName())){
						
							searchedCategory=dis;
							searchedlist.add(searchedCategory);	
						}
					}				
				}
				System.out.println("searchedlist:"+searchedlist.size());
		return  searchedlist;
	
}



	public ArrayList<Category> retrieveCategoryDataFromFile() throws IOException{
		String dataofFile=null;
		Category category=null;
		ArrayList<Category> categoryList=new ArrayList<Category>();;
		FileReader r=null;
		BufferedReader br=null;
		try {
			r=new FileReader(Constants.CATEGORYTFILE);
			br=new BufferedReader(r);
			while((dataofFile=br.readLine())!=null){
				System.out.println(dataofFile);
				
				List<String> categoryString = Arrays.asList(dataofFile.split(","));
				for(int z=0;z<=categoryString.size();z++)
				{
					
					category=new Category();
					category.setCategoryCode(categoryString.get(0));
					category.setCategoryName(categoryString.get(1));
					categoryList.add(category);
					break;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch b

			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			br.close();
		}
	return categoryList;
	}

	public ArrayList deleteCategoryData(Category category) throws IOException {
		ArrayList<Category> categoryList=null;
		if(category!=null)
		{
			categoryList=retrieveCategoryDataFromFile();
		
	  
	             
	      //  displayMemberFile(memberList);
	        BufferedWriter bout = new BufferedWriter(new FileWriter(Constants.CATEGORYTFILE));
	        Iterator<Category> iterator = categoryList.iterator();
			while (iterator.hasNext()) {
				
				
				Category cat=iterator.next();
				if(cat.getCategoryCode().equalsIgnoreCase(category.getCategoryCode()))
				{
					System.out.println("Member has been removed successfully!!");
					categoryList.remove(category);
					break;
				}
				else
				{
					System.out.println("Data not found");
					
					
				}
				String line=cat.getCategoryCode()+","+cat.getCategoryName();
				System.out.println(line);
				try {
					bout.write(line);
					bout.newLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}    
			 System.out.println("After deletion:"+categoryList.size());
			bout.close();						
		}
		return categoryList;
	}


}
