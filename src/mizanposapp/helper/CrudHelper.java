/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class CrudHelper {

    File flheader = new File("configtable.json");
    File flheaderpopup = new File("configtablepopup.json");

    public CrudHelper() {
        if (!flheader.exists() || !flheader.exists()) {
            try {
                flheader.createNewFile();
                flheaderpopup.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(CrudHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
        }
    }

    public String getheaders() {
        StringBuilder sbheader = new StringBuilder();
        try {
            BufferedReader brheader = new BufferedReader(new FileReader(flheader));
            String lineheader = "";
            while ((lineheader = brheader.readLine()) != null) {
                sbheader.append(lineheader);
            }
            brheader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CrudHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CrudHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sbheader.toString();
    }

    public String getheaderpopups() {
        StringBuilder sbheader = new StringBuilder();
        try {
            BufferedReader brheader = new BufferedReader(new FileReader(flheaderpopup));
            String lineheader = "";
            while ((lineheader = brheader.readLine()) != null) {
                sbheader.append(lineheader);
            }
            brheader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CrudHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CrudHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sbheader.toString();
    }

    public String getdatas(String alamat) {
        StringBuilder sbdata = new StringBuilder();
        try {
            URL url = new URL(Staticvar.url + alamat);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("GET");
            huc.connect();
            BufferedReader brdata = new BufferedReader(new InputStreamReader(huc.getInputStream()));
            String linedata = "";
            while ((linedata = brdata.readLine()) != null) {
                sbdata.append(linedata);
            }
            brdata.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CrudHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CrudHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(sbdata.toString());
        return sbdata.toString();
    }

    public String getdatadetails(String alamat, String param) {
        StringBuilder sbdata = new StringBuilder();
        try {
            URL url = new URL(Staticvar.url + alamat);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("POST");
            huc.setDoInput(true);
            huc.setDoOutput(true);
            huc.connect();
            OutputStream os = huc.getOutputStream();
            os.write(param.getBytes());
            BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                sbdata.append(line);
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CrudHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CrudHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(sbdata.toString());
        return sbdata.toString();
    }

    public void insertdata(String alamat, String param) {
        try {
            URL url = new URL(Staticvar.url + alamat);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("POST");
            huc.setDoInput(true);
            huc.setDoOutput(true);
            huc.connect();
            OutputStream os = huc.getOutputStream();
            os.write(param.getBytes());
            BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            os.flush();
            os.close();
            br.close();
            Staticvar.getresult = sb.toString();
        } catch (MalformedURLException ex) {
            Logger.getLogger(CrudHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CrudHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updatedata(String alamat, String param, String id) {
        try {
            URL url = new URL(Staticvar.url + alamat);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("POST");
            huc.setDoInput(true);
            huc.setDoOutput(true);
            huc.connect();
            OutputStream os = huc.getOutputStream();
            String params = param + "&id=" + id;
            os.write(params.getBytes());
            BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            os.flush();
            os.close();
            br.close();
            Staticvar.getresult = sb.toString();
        } catch (MalformedURLException ex) {
            Logger.getLogger(CrudHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CrudHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deletedata(String alamat, String param) {
        try {
            URL url = new URL(Staticvar.url + alamat);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("POST");
            huc.setDoInput(true);
            huc.setDoOutput(true);
            huc.connect();
            OutputStream os = huc.getOutputStream();
            os.write(param.getBytes());
            BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            os.flush();
            os.close();
            br.close();
            System.out.println(sb.toString());
            Staticvar.getresult = sb.toString();
        } catch (MalformedURLException ex) {
            Logger.getLogger(CrudHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CrudHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getdata(String header, DefaultTableModel dtm, String alamat) {
        Staticvar.stylecolum.clear();
        String[] data = null;
        try {
            URL url = new URL(Staticvar.url + alamat);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("GET");
            huc.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            //dapatkan header
            JSONParser jpheader = new JSONParser();
            Object objheader = jpheader.parse(new FileReader(flheader));
            JSONObject joheader = (JSONObject) objheader;
            JSONArray jaheader = (JSONArray) joheader.get(header);

            //perulangan mengambil header
            for (int i = 0; i < jaheader.size(); i++) {
                JSONObject jodata = (JSONObject) jaheader.get(i);
                JSONArray jaaray = (JSONArray) jodata.get("key");
                if (jaaray.get(2).equals("1")) {
                    dtm.addColumn(jaaray.get(1));
                    Staticvar.stylecolum.add(Integer.parseInt(String.valueOf(jaaray.get(3))));
                }
            }

            JSONArray janama = (JSONArray) joheader.get(header);
            JSONParser jpdata = new JSONParser();
            Object objdata = jpdata.parse(sb.toString());
            JSONArray jadata = (JSONArray) objdata;
            for (int i = 0; i < jadata.size(); i++) {
                JSONObject joindata = (JSONObject) jadata.get(i);
                Object[] objindata = new Object[janama.size()];
                for (int j = 0; j < objindata.length; j++) {
                    JSONObject joinindata = (JSONObject) jadata.get(i);
                    JSONObject joininindata = (JSONObject) janama.get(j);
                    JSONArray jainaray = (JSONArray) joininindata.get("key");
                    if (jainaray.get(2).equals("1")) {
                        objindata[j] = joindata.get(jainaray.get(0));
                    }
                }
                dtm.addRow(objindata);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CrudHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CrudHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(CrudHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getdatadetail(String header, DefaultTableModel dtm, String alamat, String param) {
        Staticvar.stylecolum.clear();
        String[] data = null;
        try {
            URL url = new URL(Staticvar.url + alamat);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("POST");
            huc.setDoInput(true);
            huc.setDoOutput(true);
            huc.connect();
            OutputStream os = huc.getOutputStream();
            os.write(param.getBytes());
            BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            JSONParser jpheader = new JSONParser();
            Object objheader = jpheader.parse(new FileReader(flheader));
            JSONObject joheader = (JSONObject) objheader;
            JSONArray jaheader = (JSONArray) joheader.get(header);
            for (int i = 0; i < jaheader.size(); i++) {
                JSONObject jodata = (JSONObject) jaheader.get(i);
                JSONArray jaaray = (JSONArray) jodata.get("key");
                if (jaaray.get(2).equals("1")) {
                    dtm.addColumn(jaaray.get(1));
                    Staticvar.stylecolum.add(Integer.parseInt(String.valueOf(jaaray.get(3))));
                }
            }

            JSONArray janama = (JSONArray) joheader.get(header);
            JSONParser jpdata = new JSONParser();
            Object objdata = jpdata.parse(sb.toString());
            JSONArray jadata = (JSONArray) objdata;
            for (int i = 0; i < jadata.size(); i++) {
                JSONObject joindata = (JSONObject) jadata.get(i);
                Object[] objindata = new Object[janama.size()];
                for (int j = 0; j < objindata.length; j++) {
                    JSONObject joinindata = (JSONObject) jadata.get(i);
                    JSONObject joininindata = (JSONObject) janama.get(j);
                    JSONArray jainaray = (JSONArray) joininindata.get("key");
                    if (jainaray.get(2).equals("1")) {
                        objindata[j] = joindata.get(jainaray.get(0));
                    }
                }
                dtm.addRow(objindata);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CrudHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CrudHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(CrudHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
