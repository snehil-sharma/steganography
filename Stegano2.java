import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

class Stegano2{
	private static BufferedImage image;
	private static FileInputStream in;
	private static int width, height;

	public static void main(String[] args) throws Exception {
		in =  new FileInputStream("result.jpg");
		image = ImageIO.read(in);
		width = image.getWidth();
		height = image.getHeight();
		unhide();
		new BufferedReader(new InputStreamReader(System.in)).readLine();
	}

	private static void unhide(){
		int length = image.getRGB(width-1,height-1) & 16777215;
		byte[] by = new byte[length];
		int k=0,c=7,pixel,r,g,b,f=0,t=0;
		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){
				if(k == length){
					f = 1;
					break;
				}
				pixel = image.getRGB(x,y);
				r = (pixel >> 16) & 255;
				g = (pixel >> 8) & 255;
				b = pixel & 255;
				if(c < 0){ by[k] = (byte)t; k++; c = 7; t = 0; }
				if(k != length){
					t = t | ((r & 1) << c);		c--;
				}
				if(c < 0){ by[k] = (byte)t; k++; c = 7; t = 0; }
				if(k != length){
					t = t | ((g & 1) << c);		c--;
				}
				if(c < 0){ by[k] = (byte)t; k++; c = 7; t = 0; }
				if(k != length){
					t = t | ((b & 1) << c);		c--;
				}
			}
			if(f == 1)
				break;
		}
		String s = new String(by);
		System.out.println(s);
	}
}