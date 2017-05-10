package oracleconnection;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import javax.swing.JFileChooser;

public class pdfWriter
{

    final JFileChooser fc = new JFileChooser();
    private static final Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static final Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static final Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    public pdfWriter()
    {

    }

    public String getPath()
    {
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) //se salvou
        {
            return fc.getSelectedFile().getAbsolutePath();
        } else
        {
            return null;
        }

    }

    private static void addEmptyLine(Paragraph paragraph, int number)
    {
        for (int i = 0; i < number; i++)
        {
            paragraph.add(new Paragraph(" "));
        }
    }

    public static void addTitlePage(Document document, PdfPTable table)
            throws DocumentException
    {
        Paragraph preface = new Paragraph();

        addEmptyLine(preface, 1);

        preface.add(new Paragraph("Relatório", catFont));

        addEmptyLine(preface, 3);
        preface.add(new Paragraph("Recupere os filmes na base de dados "
                + "que atendam as preferências de legenda e idioma "
                + "definidas por "
                + "um dado perfil e que pelo menos um "
                + "amigo tenha assistido ou comentado",
                smallBold));
        addEmptyLine(preface, 2);
        document.add(preface);
        document.add(table);
    }
}
