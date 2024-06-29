package com.ruvianfabrin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
// java -jar ImageCutter.jar
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o caminho da pasta onde estão as imagens:");
        String directoryPath = scanner.nextLine();
        System.out.println("Digite a altura dos pedaços em pixels:");
        int pieceHeight = scanner.nextInt();

        File dir = new File(directoryPath);
        String croppedImagesPath = directoryPath + File.separator + "cortadas";
        File croppedDir = new File(croppedImagesPath);
        if (!croppedDir.exists()) {
            croppedDir.mkdir();
        }

        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            int seq = 0;
            for (File child : directoryListing) {
                seq+=1;
                if (child.isFile() && (child.getName().endsWith(".jpg") || child.getName().endsWith(".png"))) {
                    try {
                        BufferedImage originalImage = ImageIO.read(child);
                        int width = originalImage.getWidth();
                        int totalHeight = originalImage.getHeight();
                        int count = 0;
                        String baseName = child.getName().substring(0, child.getName().lastIndexOf("."));
                        for (int y = 0; y < totalHeight; y += pieceHeight) {
                            int height = Math.min(pieceHeight, totalHeight - y);
                            BufferedImage croppedImage = originalImage.getSubimage(0, y, width, height);
                            File outputfile = new File(croppedDir, "cortada_" +baseName+"_"+ count + "_" + child.getName());
                            ImageIO.write(croppedImage, "jpg", outputfile);
                            System.out.println("Parte " + count + " salva como: " + outputfile.getAbsolutePath());
                            count++;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("O diretório não existe ou não é um diretório.");
        }
        scanner.close();
    }
}
