package com.dupleit.housecalculator;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewProduct extends AppCompatActivity {
    ListView ProductView;
    private List<customProductList> ProdcutList = new ArrayList<customProductList>();
    private customProductData adapter;
    public ProgressDialog pDialog;
    ArrayList<customProductList> selectedlist = new ArrayList<customProductList>();
    Button Btn;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        ProductView = (ListView) findViewById(R.id.NewproductList);
        adapter = new customProductData(this, ProdcutList);
        ProductView.setAdapter(adapter);
        new GetAllProducts().execute();
        fab = (FloatingActionButton) findViewById(R.id.PrintNow);

       // Btn = (Button)findViewById(R.id.PrintNow);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //to get selected list items and store to arraylist and send to next activity
                selectedlist.clear();
                Log.e("selecetd list",selectedlist.size()+"");
                for(int i=0;i<ProdcutList.size();i++)
                {
                    if(ProdcutList.get(i).getEtprice().length()>0&&ProdcutList.get(i).getEtqty().length()>0) {
                        Log.e("price",ProdcutList.get(i).getEtprice()+"'");
                        selectedlist.add(ProdcutList.get(i));

                    }
                }
                //send data to print activity
                if(selectedlist.size()>0)
                {
                    Log.e("selected item size",selectedlist.size()+"");
                    //Intent print=new Intent();
                    //print.putParcelableArrayListExtra("data",selectedlist);
                    //startActivity(print);
                    createandDisplayPdf(selectedlist);
                }

            }
        });

    }

    /*To get all the product and set in adapter*/
    private class GetAllProducts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(NewProduct.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0)  {
            try {
                JSONObject obj = null;
                try {
                    obj = new JSONObject(loadJSONFromAsset());
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                JSONArray m_jArry = obj.getJSONArray("product");

                for (int k = 0; k < m_jArry.length(); k++) {
                    JSONObject jo_inside = m_jArry.getJSONObject(k);
                    customProductList cpl = new customProductList();
                    String product_code = jo_inside.getString("product_code");
                    String product_name = jo_inside.getString("product_name");
                    String product_unit = jo_inside.getString("unit");
                    String product_price = jo_inside.getString("product_price");
                    cpl.setProductCode(product_code);
                    cpl.setProductName(product_name);
                    cpl.setProductPrice(product_price);
                    cpl.setUnit(product_unit);
                    ProdcutList.add(cpl);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            adapter.notifyDataSetChanged();
        }

    }
    /*THis is user to get all the data in JSON file*/
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = NewProduct.this.getAssets().open("file.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        // Log.e("Error"," Json DAta"+json);
        return json;

    }

    // Method for creating a pdf file from text, saving it then opening it for display
    public void createandDisplayPdf(ArrayList text) {

        Document doc = new Document();
        String Str = null;
        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ProductCalculator";

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();


            Date d = new Date();
            java.text.DateFormat Df = android.text.format.DateFormat.getDateFormat(getApplicationContext());
            Str = d.toString()+".pdf";
            File file = new File(dir, Str);
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();
            Paragraph p1;
            p1 = new Paragraph("New Product Price List");

            Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN);
            p1.setFont(headerFont);

            p1.setAlignment(p1.ALIGN_CENTER);

            PdfPTable tb = new PdfPTable(7);
            /*Product code cell*/
            PdfPCell cell = new PdfPCell(new Phrase("Sr No."));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tb.addCell(cell);
            /*Product code cell*/
            cell = new PdfPCell(new Phrase("Product Code"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tb.addCell(cell);
            /*Product Name cell*/
            cell = new PdfPCell(new Phrase("Product Name"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tb.addCell(cell);
            /*Product Unit cell*/
            cell = new PdfPCell(new Phrase("Product Unit"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tb.addCell(cell);
            /*Price*/
            cell = new PdfPCell(new Phrase("Product Price"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tb.addCell(cell);
            /*USer Quantity*/
            cell = new PdfPCell(new Phrase("User product Quantity"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tb.addCell(cell);
            /*User Price*/
            cell = new PdfPCell(new Phrase("User product Price"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tb.addCell(cell);
            for (int i=0; i<selectedlist.size();i++){
                int j=1;

                /*p1 = new Paragraph(selectedlist.get(i).getProductName());
                Font paraFont= new Font(Font.FontFamily.TIMES_ROMAN);
                p1.setAlignment(Paragraph.ALIGN_CENTER);
                p1.setFont(paraFont);
*/
                tb.addCell(String.valueOf(j+i));
                tb.addCell(selectedlist.get(i).getProductCode());
                tb.addCell(selectedlist.get(i).getProductName());
                tb.addCell(selectedlist.get(i).getUnit());
                tb.addCell(selectedlist.get(i).getProductPrice());
                tb.addCell(selectedlist.get(i).getEtqty());
                tb.addCell(selectedlist.get(i).getEtprice());
            }
            p1.add(tb);
            //add paragraph to document
            doc.add(p1);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally {
            doc.close();
        }

        viewPdf(Str, "ProductCalculator");
    }

    // Method for opening a pdf file
    private void viewPdf(String file, String directory) {

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + directory + "/" + file);
        Uri path = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(NewProduct.this, "Can't read pdf file", Toast.LENGTH_SHORT).show();
        }
    }


}