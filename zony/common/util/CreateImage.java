// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CreateImage.java

package com.zony.common.util;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import org.apache.commons.lang.StringUtils;

public class CreateImage
{

	public CreateImage()
	{
	}

	public static String createImageByStr(int imgWidth, int imgHeight, String imgStr, String filePath, int fontSize, Color background, Color fontColor)
		throws IOException
	{
		int width = imgWidth != 0 ? imgWidth : 100;
		int height = imgHeight != 0 ? imgHeight : 100;
		if (fontSize == 0)
			fontSize = 10;
		if (StringUtils.isEmpty(imgStr))
		{
			return null;
		} else
		{
			File file = new File(filePath);
			Font font = new Font("Serif", 1, fontSize);
			BufferedImage bi = new BufferedImage(width, height, 1);
			Graphics2D g2 = (Graphics2D)bi.getGraphics();
			g2.setBackground(background);
			g2.clearRect(0, 0, width, height);
			g2.setPaint(fontColor);
			java.awt.font.FontRenderContext context = g2.getFontRenderContext();
			Rectangle2D bounds = font.getStringBounds(imgStr, context);
			double x = 2D;
			double y = ((double)height - bounds.getHeight()) / 2D;
			double ascent = -bounds.getY();
			double baseY = y + ascent;
			g2.drawString(imgStr, 2, (int)baseY);
			ImageIO.write(bi, "jpg", file);
			return filePath;
		}
	}

	public static InputStream getCreateImageStream(int imgWidth, int imgHeight, String imgStr, String filePath, int fontSize, Color background, Color fontColor)
		throws IOException
	{
		filePath = createImageByStr(imgWidth, imgHeight, imgStr, filePath, fontSize, background, fontColor);
		FileInputStream fileInputStream = null;
		if (!StringUtils.isEmpty(filePath))
		{
			File file = new File(filePath);
			if (file.exists())
				fileInputStream = new FileInputStream(filePath);
		}
		return fileInputStream;
	}

	public static void deleteCreateImg(String filePath)
	{
		File fille = new File(filePath);
		if (fille.exists())
			fille.delete();
	}

	public static void main(String args[])
	{
		String msg = "³ÉÓïakjjdflkaslkjflkajlkslfdËµÃ÷";
		int nameWidth = msg.length() * 14;
		String newImgPath = "d:\\zonysoft\\temp\\1.jpg";
		try
		{
			createImageByStr(nameWidth, 14, msg, "d:\\zonysoft\\temp\\1.jpg", 12, Color.WHITE, Color.black);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
