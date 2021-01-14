package Problem; /**
 * Created by Henri on 29/07/2019.
 */


import java.io.*;

public class Arquivo2
{
    public static final int LEITURA = 0;
    public static final int ESCRITA = 1;

    private FileInputStream fIS;
    private InputStreamReader iSR;
    private BufferedReader bR;
    private FileWriter fW;
    private PrintWriter pW;


    public Arquivo2(String nome, int tipo,String dir)
    {
        abreArquivo(nome, tipo,dir);
    }

    public String leLinha()
    {
        String linha = null;
        try
        {
            do
            {
                linha = bR.readLine();
            } while(linha.equals(""));
        } catch(IOException e)
        {
            e.printStackTrace();
        }

        return linha;
    }

    public void escreveArquivo(String texto)
    {
        pW.println(texto);
    }
    public void escreveArquivo2(String texto)
    {
        pW.print(texto);
    }

    public void abreArquivo(String nome, int tipo, String diret)
    {
        try
        {
            if (tipo == LEITURA)
            {
                fIS = new FileInputStream(nome);
                iSR = new InputStreamReader(fIS);
                bR = new BufferedReader(iSR);
            }
            else
            {
                File dir = new File(diret);
                dir.mkdirs();
                File file = new File(dir, nome);
                fW = new FileWriter(file);
                pW = new PrintWriter(fW);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
            fechaArquivo();
        }
    }

    public void fechaArquivo()
    {
        try
        {
            if (fIS != null) fIS.close();
            if (iSR != null) iSR.close();
            if (bR != null) bR.close();
            if (fW != null) fW.close();
            if (pW != null) pW.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}