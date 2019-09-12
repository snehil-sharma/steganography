import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

class Stegano{
	private static BufferedImage image, output;
	private static FileInputStream in;
	private static int width, height;

	public static void main(String[] args) throws Exception {
		in =  new FileInputStream("image.jpg");
		image = ImageIO.read(in);
		width = image.getWidth();
		height = image.getHeight();
		hide();
	}

	private static void hide() throws Exception{
		System.out.println("Enter the text:");
		String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
		byte[] by = s.getBytes();
		output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		File o = new File("result.jpg");
		int pixel,r,g,b,k=0,c=7,f=0;
		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){
				if(k == by.length){
					f = x;
					break;
				}
				pixel = image.getRGB(x,y);
				r = (pixel >> 16) & 255;
				g = (pixel >> 8) & 255;
				b = pixel & 255;
				if(c < 0){ k++; c = 7; }
				if(k != by.length){
					r = (r & 254) | ((new Byte(by[k]).intValue() >> c) & 1);	c--;
				}
				if(c < 0){ k++; c = 7; }
				if(k != by.length){
					g = (g & 254) | ((new Byte(by[k]).intValue() >> c) & 1);	c--;
				}
				if(c < 0){ k++; c = 7; }
				if(k != by.length){
					b = (b & 254) | ((new Byte(by[k]).intValue() >> c) & 1);	c--;
				}
				pixel = ((r<<16) | (g<<8) | b);
				output.setRGB(x,y,pixel);
			}
			for(int x=f; x<width; x++){
				pixel = image.getRGB(x,y);
				output.setRGB(x,y,pixel);
			}
		}
		output.setRGB(width-1,height-1,by.length);
		ImageIO.write(output, "bmp" , o);
	}
}