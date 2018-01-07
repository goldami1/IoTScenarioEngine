package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class serverImgManager
{
	private static serverImgManager m_Instance = null;
	
	private boolean isDirFound(String i_dirRelativePath)
	{
		return (new File(i_dirRelativePath)).exists();
	} 
	
	@SuppressWarnings("finally")
	private boolean createDir(String i_dirRelativePath)
	{
		File file = null;
		boolean isSucceed = false;
		
		try
		{
			file = new File(Constants.getVendorsLogosFolderName());
			isSucceed = file.mkdir();
			isSucceed = true;
		}catch(Exception e) {System.out.println("Error creating main vendors logos dir");}
		finally
		{
			return isSucceed;
		}
	}
	
	private boolean isMainDirFound()
	{
		return isDirFound(Constants.getVendorsLogosFolderName());
	}
	
	private boolean createMainDir()
	{
		return createDir(Constants.getVendorsLogosFolderName());
	}
	
	@SuppressWarnings("finally")
	private String createNewUser(String i_relativePath ,String i_name, BufferedImage i_img)
	{
		String relativePathAndFileName = i_relativePath+"/"+i_name+Constants.getImgSavingFormatName();
		File outputFile=null;
		boolean isSucceed=false;
		
		
		if(!isDirFound(i_relativePath))
		{
			createDir(i_relativePath);
		}
		
		
		try
		{
			outputFile = new File(relativePathAndFileName);
			ImageIO.write(i_img, Constants.getImgSavingFormatName().substring(1), outputFile);
			isSucceed = true;
		}catch(Exception e) {System.out.println(e.getMessage());}
		
		finally
		{
			if(isSucceed)
				return i_relativePath;
			else
				return null;
		}
	}
	
	public String createNewCustomer(String i_name, BufferedImage i_pic)
	{
		String relativePath = Constants.getCustomersPicsFolderName()+"/"+i_name;
		return createNewUser(relativePath, i_name, i_pic);
	}
	
	public String createNewVendor(String i_name, BufferedImage i_logo)
	{
		String relativePath = Constants.getVendorsLogosFolderName()+"/"+i_name;
		return createNewUser(relativePath, i_name, i_logo);
	}

	public static serverImgManager getInstance()
	{
		if(m_Instance == null)
		{
			m_Instance = new serverImgManager();
		}
		
		return m_Instance;
		
	}
	
 	protected serverImgManager()
	{
		if(!isMainDirFound())
			createMainDir();
	}
	
	@SuppressWarnings("finally")
	public static BufferedImage getImage(String i_relativePath)
	{
		File imageFile = null;
		BufferedImage res = null;
		
		try
		{
			imageFile = new File(i_relativePath);
			if(imageFile.exists())
				res = ImageIO.read(imageFile);
		} catch (IOException e) {System.out.println(e.getMessage());}
		finally
		{
			return res;
		}
	}
	
}
