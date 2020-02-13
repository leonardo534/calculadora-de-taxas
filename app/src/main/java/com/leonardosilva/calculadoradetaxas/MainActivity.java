package com.leonardosilva.calculadoradetaxas;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.math.BigDecimal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String KEY_TAXA_DEBITO_CLIENTE = "taxa_debito_cliente";
    private static final String KEY_TAXA_DEBITO_2_PF = "taxa_debito_2_pf";
    private static final String KEY_TAXA_DEBITO_30_PF = "taxa_debito_30_pf";
    private static final String KEY_TAXA_DEBITO_2_PJ = "taxa_debito_2_pj";
    private static final String KEY_TAXA_DEBITO_30_PJ = "taxa_debito_30_pj";
    private static final String KEY_TAXA_DEBITO = "taxa_debito";
    private static final String KEY_TAXA_DEBITO_FAT_MENOR_50 = "taxa_debito_fat_menor_50";
    private static final String KEY_TAXA_DEBITO_FAT_MAIOR_50 = "taxa_debito_fat_maior_50";
    private static final String KEY_TAXA_DEBITO_NA_HORA = "taxa_debito_na_hora";
    private static final String KEY_TAXA_DEBITO_14 = "taxa_debito_14";
    private static final String KEY_TAXA_DEBITO_30 = "taxa_debito_30";
    private static final String KEY_TAXA_DEBITO_30_TARJA = "taxa_debito_30_tarja";

    private static final String KEY_CREDITO_PARCELADO = "taxa_credito_parcelado";
    private static final String KEY_CREDITO_PARCELADO_2_PF = "taxa_credito_parcelado_2_pf";
    private static final String KEY_CREDITO_PARCELADO_30_PF = "taxa_credito_parcelado_30_pf";
    private static final String KEY_CREDITO_PARCELADO_2_PJ = "taxa_credito_parcelado_2_pj";
    private static final String KEY_CREDITO_PARCELADO_30_PJ = "taxa_credito_parcelado_30_pj";
    private static final String KEY_CREDITO_PARCELADO_2_FAT_MENOR_50 = "taxa_credito_parcelado_2_fat_menor_50";
    private static final String KEY_CREDITO_PARCELADO_30_FAT_MENOR_50 = "taxa_credito_parcelado_30_fat_menor_50";
    private static final String KEY_CREDITO_PARCELADO_2_FAT_MAIOR_50 = "taxa_credito_parcelado_2_fat_maior_50";
    private static final String KEY_CREDITO_PARCELADO_30_FAT_MAIOR_50 = "taxa_credito_parcelado_30_fat_maior_50";
    private static final String KEY_CREDITO_PARCELADO_NA_HORA = "taxa_credito_parcelado_na_hora";
    private static final String KEY_CREDITO_PARCELADO_14 = "taxa_credito_parcelado_14";
    private static final String KEY_CREDITO_PARCELADO_30 = "taxa_credito_parcelado_30";
    private static final String KEY_CREDITO_PARCELADO_30_TARJA = "taxa_credito_parcelado_30_tarja";

    private static final String KEY_CREDITO_A_VISTA = "taxa_credito_a_vista";
    private static final String KEY_CREDITO_A_VISTA_VELOZ = "taxa_credito_a_vista_veloz";
    private static final String KEY_CREDITO_A_VISTA_CONTROLE = "taxa_credito_a_vista_controle";
    private static final String KEY_CREDITO_A_VISTA_2_PF = "taxa_credito_a_vista_2_pf";
    private static final String KEY_CREDITO_A_VISTA_30_PF = "taxa_credito_a_vista_30_pf";
    private static final String KEY_CREDITO_A_VISTA_2_PJ = "taxa_credito_a_vista_2_pj";
    private static final String KEY_CREDITO_A_VISTA_30_PJ = "taxa_credito_a_vista_30_pj";
    private static final String KEY_CREDITO_A_VISTA_2_FAT_MENOR_50 = "taxa_credito_a_vista_2_fat_menor_50";
    private static final String KEY_CREDITO_A_VISTA_30_FAT_MENOR_50 = "taxa_credito_a_vista_30_fat_menor_50";
    private static final String KEY_CREDITO_A_VISTA_2_FAT_MAIOR_50 = "taxa_credito_a_vista_2_fat_maior_50";
    private static final String KEY_CREDITO_A_VISTA_30_FAT_MAIOR_50 = "taxa_credito_a_vista_30_fat_maior_50";
    private static final String KEY_CREDITO_A_VISTA_NA_HORA = "taxa_credito_a_vista_na_hora";
    private static final String KEY_CREDITO_A_VISTA_14 = "taxa_credito_a_vista_14";
    private static final String KEY_CREDITO_A_VISTA_30 = "taxa_credito_a_vista_30";
    private static final String KEY_CREDITO_A_VISTA_30_TARJA = "taxa_credito_a_vista_30_tarja";

    private static final String KEY_PARCELAMENTO = "taxa_de_parcelamento";
    private static final String KEY_PARCELAMENTO_2_6 = "taxa_de_parcelamento_2_6";
    private static final String KEY_PARCELAMENTO_7_12 = "taxa_de_parcelamento_7_12";
    private static final String KEY_PARCELAMENTO_2_PF = "taxa_de_parcelamento_2_pf";
    private static final String KEY_PARCELAMENTO_30_PF = "taxa_de_parcelamento_30_pf";
    private static final String KEY_PARCELAMENTO_2_PJ = "taxa_de_parcelamento_2_pj";
    private static final String KEY_PARCELAMENTO_30_PJ = "taxa_de_parcelamento_30_pj";
    private static final String KEY_PARCELAMENTO_NA_HORA = "taxa_de_parcelamento_na_hora";
    private static final String KEY_PARCELAMENTO_14 = "taxa_de_parcelamento_14";
    private static final String KEY_PARCELAMENTO_30 = "taxa_de_parcelamento_30";
    private static final String KEY_PARCELAMENTO_30_tarja = "taxa_de_parcelamento_30_tarja";

    Spinner spModelo;
    Spinner spParcelas;
    Spinner spTaxas;
    EditText editResultado;
    TextView textViewResultadosUsuario;
    TextView textViewResultadosCliente;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        inicializarComponentes();
        inicializarFirebase();

        ArrayAdapter<CharSequence> arrayMaquinetas = ArrayAdapter.createFromResource(this, R.array.Maquinetas
                , R.layout.spinner_center);
        arrayMaquinetas.setDropDownViewResource(R.layout.spinner_center);
        spModelo.setAdapter(arrayMaquinetas);

        ArrayAdapter<CharSequence> arrayParcelas = ArrayAdapter.createFromResource(this, R.array.parcelas, android.R.layout.simple_spinner_item);
        arrayParcelas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spParcelas.setAdapter(arrayParcelas);

        ArrayAdapter<CharSequence> arrayTaxas = ArrayAdapter.createFromResource(this, R.array.taxas, android.R.layout.simple_spinner_item);
        arrayTaxas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTaxas.setAdapter(arrayTaxas);

        Typeface font = Typeface.createFromAsset(getAssets(), "sf-regular.otf");
        editResultado.setTypeface(font);

        editResultado.addTextChangedListener(new MoneyTextWatcher(editResultado));

    } // FIM DO ONCREATE

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(MainActivity.this);

    }

    private void inicializarComponentes() {
        spModelo = findViewById(R.id.spModelo);
        spParcelas = findViewById(R.id.spinnerParcelas);
        spTaxas = findViewById(R.id.spinnerTaxas);
        editResultado = findViewById(R.id.editResultado);
        textViewResultadosUsuario = findViewById(R.id.textViewResultadosUsuario);
        textViewResultadosCliente = findViewById(R.id.textViewResultadosCliente);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void btnResultados(View view) {
        if (editResultado.getText().length() != 0) {
            String modelo;
            modelo = spModelo.getSelectedItem().toString();
            if (isOnline() == false) {
                Toast.makeText(this, "Conecte-se a internet para melhor experiencia", Toast.LENGTH_SHORT).show();
            }
            buscarMaquineta(modelo);
            Log.d("TAG", modelo);

        } else {
            Toast.makeText(this, "Informe um valor", Toast.LENGTH_LONG).show();
            editResultado.requestFocus();
        }
    }

    public boolean isOnline() {
        boolean state;
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            state = true;
        } else {
            state = false;
        }

        return state;
    }

    public void buscarMaquineta(String maquineta) {
        switch (maquineta) {
            case "Sumup":
                sumup(maquineta.toLowerCase(), unmask(editResultado.getText().toString()), spParcelas.getSelectedItem().toString(), spTaxas.getSelectedItem().toString());
                break;

            case "PagSeguro":
                pagseguro(maquineta.toLowerCase(), unmask(editResultado.getText().toString()), spParcelas.getSelectedItem().toString(), spTaxas.getSelectedItem().toString());
                break;

            case "Mercado Pago":
                mercadoPago(maquineta.toLowerCase(), unmask(editResultado.getText().toString()), spParcelas.getSelectedItem().toString(), spTaxas.getSelectedItem().toString());
                break;

            case "Ifood":
                ifood(maquineta.toLowerCase(), unmask(editResultado.getText().toString()), spParcelas.getSelectedItem().toString(), spTaxas.getSelectedItem().toString());
                break;

            case "BEVImais":
                bevimais(maquineta.toLowerCase(), unmask(editResultado.getText().toString()), spParcelas.getSelectedItem().toString(), spTaxas.getSelectedItem().toString());
                break;

            case "BEVI":
                bevi(maquineta.toLowerCase(), unmask(editResultado.getText().toString()), spParcelas.getSelectedItem().toString(), spTaxas.getSelectedItem().toString());
                break;

            case "StarPay":
                starpay(maquineta.toLowerCase(), unmask(editResultado.getText().toString()), spParcelas.getSelectedItem().toString(), spTaxas.getSelectedItem().toString());
                break;
            default:
                Toast.makeText(this, "Modelo em manutenção", Toast.LENGTH_SHORT).show();
        }
    }

    public void sumup(final String maquineta, final String valor, final String opTaxa, final String opParcela) {
        if (editResultado.getText().length() == 0) {
            Toast.makeText(this, "Informe um valor", Toast.LENGTH_SHORT).show();
            editResultado.requestFocus();
        } else {
            db.collection(maquineta).document(maquineta).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            if (documentSnapshot.exists()) {
                                Double debito_na_hora = documentSnapshot.getDouble(KEY_TAXA_DEBITO);
                                Double debito_30 = documentSnapshot.getDouble(KEY_TAXA_DEBITO_30);
                                Double credito_a_vista = documentSnapshot.getDouble(KEY_CREDITO_A_VISTA);
                                Double credito_a_vista_30 = documentSnapshot.getDouble(KEY_CREDITO_A_VISTA_30);
                                Double credito_parcelado = documentSnapshot.getDouble(KEY_CREDITO_PARCELADO);
                                Double credito_parcelado_30 = documentSnapshot.getDouble(KEY_CREDITO_PARCELADO_30);
                                Double taxa_parcelamento = documentSnapshot.getDouble(KEY_PARCELAMENTO);
                                Double taxa_parcelamento_30 = documentSnapshot.getDouble(KEY_PARCELAMENTO_30);

                                if (opTaxa.equals("Debito")) {

                                    if (opParcela.equals("Assumir")) { // TA OK
                                        Double valor1 = Double.parseDouble(unmask(valor.trim()));
                                        BigDecimal valor2 = new BigDecimal(valor1);
                                        BigDecimal valorCobrar_1Dia = new BigDecimal(valor1 - (valor1 * (debito_na_hora * 100)) / 100);
                                        BigDecimal valorCobrar_30Dias = new BigDecimal(valor1 - (valor1 * (debito_30 * 100)) / 100);

                                        textViewResultadosUsuario.setText("Valor a receber\n" +
                                                "Débito 1 dia: " + colocarVirgula(String.valueOf(valorCobrar_1Dia.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nDébito 30 dias: " + colocarVirgula(String.valueOf(valorCobrar_30Dias.setScale(0, BigDecimal.ROUND_UP))));
                                        textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                colocarVirgula(String.valueOf(valor2.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\n" + colocarVirgula(String.valueOf(valor2.setScale(0, BigDecimal.ROUND_UP))));

                                    } else { // REPASSAR OK

                                        Double valor1 = Double.parseDouble(unmask(valor).trim());
                                        BigDecimal valorCobrar_1Dia = new BigDecimal(valor1 + (valor1 * (debito_na_hora * 100)) / 100);
                                        BigDecimal valorCobrar_30Dias = new BigDecimal(valor1 + (valor1 * (debito_30 * 100)) / 100);
                                        BigDecimal parcelaCliente_1Dia = new BigDecimal(valor1 + (valor1 * (debito_na_hora * 100)) / 100);
                                        BigDecimal parcelaCliente_30Dias = new BigDecimal(valor1 + (valor1 * (debito_30 * 100)) / 100);

                                        textViewResultadosUsuario.setText("Valor a cobrar\n" +
                                                "Débito 1 dia: " + colocarVirgula(String.valueOf(valorCobrar_1Dia.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nDébito 30 dias: " + colocarVirgula(String.valueOf(valorCobrar_30Dias.setScale(0, BigDecimal.ROUND_UP))));
                                        textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                colocarVirgula(String.valueOf(parcelaCliente_1Dia.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                colocarVirgula(String.valueOf(parcelaCliente_30Dias.setScale(0, BigDecimal.ROUND_UP))));
                                    }
                                } else if (opTaxa != "Debito") {

                                    if (opParcela.equals("Assumir")) { // OK
                                        Double valor1 = Double.parseDouble(unmask(valor));
                                        Double opTaxa1 = Double.parseDouble(unmask(opTaxa));
                                        BigDecimal parcelaCliente_1Dia = new BigDecimal(valor1 / opTaxa1);
                                        BigDecimal parcelaCliente_30Dias = new BigDecimal(valor1 / opTaxa1);
                                        BigDecimal valorReceber_1Dia = new BigDecimal(valor1 - ((valor1 * ((credito_parcelado * 100) + (taxa_parcelamento * (opTaxa1 - 1)) * 100) / 100)));
                                        BigDecimal valorReceber_30Dias = new BigDecimal(valor1 - (valor1 * ((credito_parcelado_30 * 100) + (taxa_parcelamento_30 * 100)) / 100));

                                        textViewResultadosUsuario.setText("Valor a receber\n" +
                                                "1 dia útil: " + colocarVirgula(String.valueOf(valorReceber_1Dia.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nA cada 30 dias:" + colocarVirgula(String.valueOf(valorReceber_30Dias.setScale(0, BigDecimal.ROUND_UP))));

                                        textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_1Dia.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30Dias.setScale(0, BigDecimal.ROUND_UP))));
                                    } else { // REPASSAR // TA OK
                                        Double valor1 = Double.parseDouble(unmask(valor));
                                        Double opTaxa1 = Double.parseDouble(unmask(opTaxa));
                                        BigDecimal parcelaCliente_1Dia = new BigDecimal((100 * valor1) / (100 - ((credito_parcelado * 100) + ((taxa_parcelamento * (opTaxa1 - 1)) * 100))) / opTaxa1);
                                        BigDecimal valorCobrar_1Dia = new BigDecimal((100 * valor1) / (100 - ((credito_parcelado * 100) + ((taxa_parcelamento * (opTaxa1 - 1)) * 100))));
                                        BigDecimal parcelaCliente_30Dias = new BigDecimal((valor1 + (valor1 * ((credito_parcelado_30 * 100) + (taxa_parcelamento_30 * 100)) / 100)) / opTaxa1);
                                        BigDecimal valorCobrar_30Dias = new BigDecimal(valor1 + (valor1 * ((credito_parcelado_30 * 100) + (taxa_parcelamento_30 * 100)) / 100));

                                        textViewResultadosUsuario.setText("Valor a cobrar\n" +
                                                "1 dia útil: " + colocarVirgula(String.valueOf(valorCobrar_1Dia.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nA cada 30 dias: " + colocarVirgula(String.valueOf(valorCobrar_30Dias.setScale(0, BigDecimal.ROUND_UP))));

                                        textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_1Dia.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30Dias.setScale(0, BigDecimal.ROUND_UP))));
                                    }
                                }

                            } else {
                                Log.d("TAGG", "Documento não existe");
                            }

                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
    }

    public void pagseguro(final String maquineta, final String valor, final String opTaxa, final String opParcela) {
        if (editResultado.getText().length() == 0) {
            Toast.makeText(this, "Informe um valor", Toast.LENGTH_SHORT).show();
            editResultado.requestFocus();
        } else {
            db.collection(maquineta).document(maquineta).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            if (documentSnapshot.exists()) {
                                Double debito_cliente = documentSnapshot.getDouble(KEY_TAXA_DEBITO_CLIENTE);
                                Double debito_na_hora = documentSnapshot.getDouble(KEY_TAXA_DEBITO_NA_HORA);
                                Double debito_14 = documentSnapshot.getDouble(KEY_TAXA_DEBITO_14);
                                Double debito_30 = documentSnapshot.getDouble(KEY_TAXA_DEBITO_30);
                                Double debito_30_tarja = documentSnapshot.getDouble(KEY_TAXA_DEBITO_30_TARJA);

                                Double credito_a_vista_na_hora = documentSnapshot.getDouble(KEY_CREDITO_A_VISTA_NA_HORA);
                                Double credito_a_vista_14 = documentSnapshot.getDouble(KEY_CREDITO_A_VISTA_14);
                                Double credito_a_vista_30 = documentSnapshot.getDouble(KEY_CREDITO_A_VISTA_30);
                                Double credito_a_vista_30_tarja = documentSnapshot.getDouble(KEY_CREDITO_A_VISTA_30_TARJA);

                                Double credito_parcelado_na_hora = documentSnapshot.getDouble(KEY_CREDITO_PARCELADO_NA_HORA);
                                Double credito_parcelado_14 = documentSnapshot.getDouble(KEY_CREDITO_PARCELADO_14);
                                Double credito_parcelado_30 = documentSnapshot.getDouble(KEY_CREDITO_PARCELADO_30);
                                Double credito_parcelado_30_tarja = documentSnapshot.getDouble(KEY_CREDITO_PARCELADO_30_TARJA);

                                Double taxa_parcelamento_na_hora = documentSnapshot.getDouble(KEY_PARCELAMENTO_NA_HORA);
                                Double taxa_parcelamento_14 = documentSnapshot.getDouble(KEY_PARCELAMENTO_14);
                                Double taxa_parcelamento_30 = documentSnapshot.getDouble(KEY_PARCELAMENTO_30);
                                Double taxa_parcelamento_30_tarja = documentSnapshot.getDouble(KEY_PARCELAMENTO_30_tarja);

                                Double taxaParcelamentoTotal_na_hora;
                                Double taxaParcelamentoTotal_14;
                                Double taxaParcelamentoTotal_30;
                                Double taxaParcelamentoTotal_30_tarja;

                                Double valorParcelamentoTotal_na_hora;
                                Double valorParcelamentoTotal_14;
                                Double valorParcelamentoTotal_30;
                                Double valorParcelamentoTotal_30_tarja;

                                BigDecimal parcelaCliente = null;
                                BigDecimal parcelaCobrador = null;

                                BigDecimal valorReceber_na_hora = null;
                                BigDecimal valorReceber_14 = null;
                                BigDecimal valorReceber_30 = null;
                                BigDecimal valorReceber_30_tarja = null;

                                BigDecimal valorCobrar_na_hora = null;
                                BigDecimal valorCobrar_14 = null;
                                BigDecimal valorCobrar_30 = null;
                                BigDecimal valorCobrar_30_tarja = null;


                                if (opTaxa.equals("Debito")) { // TA OK

                                    if (opParcela.equals("Assumir")) { // TA OK
                                        Double valor1 = Double.parseDouble(unmask(valor.trim()));
                                        valorReceber_na_hora = new BigDecimal(valor1 - (valor1 * (debito_na_hora * 100)) / 100);
                                        valorReceber_14 = new BigDecimal(valor1 - (valor1 * (debito_14 * 100)) / 100);
                                        valorReceber_30 = new BigDecimal(valor1 - (valor1 * (debito_30 * 100)) / 100);
                                        valorReceber_30_tarja = new BigDecimal(valor1 - (valor1 * (debito_30_tarja * 100)) / 100);
                                        parcelaCliente = new BigDecimal(valor1);

                                        textViewResultadosUsuario.setText("Valor a receber\n" +
                                                "Débito na hora: " + colocarVirgula(String.valueOf(valorReceber_na_hora.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nDébito 14 dias: " + colocarVirgula(String.valueOf(valorReceber_14.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nDébito 30 dias: " + colocarVirgula(String.valueOf(valorReceber_30.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nDébito 30 dias Tarja: " + colocarVirgula(String.valueOf(valorReceber_30_tarja.setScale(0, BigDecimal.ROUND_UP))));

                                        textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                colocarVirgula(String.valueOf(parcelaCliente.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\n" + colocarVirgula(String.valueOf(parcelaCliente.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\n" + colocarVirgula(String.valueOf(parcelaCliente.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\n" + colocarVirgula(String.valueOf(parcelaCliente.setScale(0, BigDecimal.ROUND_UP))));

                                    } else { // REPASSAR REALMENTE OK

                                        Double valor1 = Double.parseDouble(unmask(valor.trim()));
                                        valorCobrar_na_hora = new BigDecimal(valor1 + (valor1 * (debito_cliente * 100)) / 100);
                                        valorCobrar_14 = new BigDecimal(valor1 + (valor1 * (debito_cliente * 100)) / 100);
                                        valorCobrar_30 = new BigDecimal(valor1 + (valor1 * (debito_cliente * 100)) / 100);
                                        valorCobrar_30_tarja = new BigDecimal(valor1 + (valor1 * (debito_cliente * 100)) / 100);

                                        parcelaCobrador = new BigDecimal(valor1);

                                        textViewResultadosUsuario.setText("Valor a receber\n" +
                                                "Débito na hora: " + colocarVirgula(String.valueOf(parcelaCobrador.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nDébito 14 dias: " + colocarVirgula(String.valueOf(parcelaCobrador.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nDébito 30 dias: " + colocarVirgula(String.valueOf(parcelaCobrador.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nDébito 30 dias Tarja: " + colocarVirgula(String.valueOf(parcelaCobrador.setScale(0, BigDecimal.ROUND_UP))));

                                        textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                colocarVirgula(String.valueOf(valorCobrar_na_hora.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\n" + colocarVirgula(String.valueOf(valorCobrar_14.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\n" + colocarVirgula(String.valueOf(valorCobrar_30.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\n" + colocarVirgula(String.valueOf(valorCobrar_30_tarja.setScale(0, BigDecimal.ROUND_UP))));
                                    }
                                } else if (opTaxa != "Debito") {

                                    if (opParcela.equals("Assumir")) { // REALMENTE OK
                                        Double valor1 = Double.parseDouble(unmask(valor));
                                        Double opTaxa1 = Double.parseDouble(unmask(opTaxa));

                                        Double taxaIntermediacao_na_hora = 0.0;
                                        Double taxaIntermediacao_14 = 0.0;
                                        Double taxaIntermediacao_30 = 0.0;
                                        Double taxaIntermediacao_30_tarja = 0.0;

                                        if (opTaxa1 > 1) {
                                            taxaIntermediacao_na_hora = credito_parcelado_na_hora;
                                            taxaIntermediacao_14 = credito_parcelado_14;
                                            taxaIntermediacao_30 = credito_parcelado_30;
                                            taxaIntermediacao_30_tarja = credito_parcelado_30_tarja;


                                            Double valorIntermediacao_na_hora = ((valor1 * (taxaIntermediacao_na_hora * 100)) / 100) / 100;
                                            Double valorIntermediacao_14 = ((valor1 * (taxaIntermediacao_14 * 100)) / 100) / 100;
                                            Double valorIntermediacao_30 = ((valor1 * (taxaIntermediacao_30 * 100)) / 100) / 100;
                                            Double valorIntermediacao_30_tarja = ((valor1 * (taxaIntermediacao_30_tarja * 100)) / 100) / 100;

                                            Double valorPresente_na_hora = (valor1 / opTaxa1) / Math.pow(1 + taxa_parcelamento_na_hora, opTaxa1); //valor dando certo
                                            Double valorPresente_14 = (valor1 / opTaxa1) / Math.pow(1 + taxa_parcelamento_14, opTaxa1); //valor dando certo
                                            Double valorPresente_30 = (valor1 / opTaxa1) / Math.pow(1 + taxa_parcelamento_30, opTaxa1); //valor dando certo
                                            Double valorPresente_30_tarja = (valor1 / opTaxa1) / Math.pow(1 + taxa_parcelamento_30_tarja, opTaxa1); //valor dando certo

                                            Double valorPresenteTotal_na_hora = 0.0;
                                            Double valorPresenteTotal_14 = 0.0;
                                            Double valorPresenteTotal_30 = 0.0;
                                            Double valorPresenteTotal_30_tarja = 0.0;

                                            Double desagioTotal_na_hora = 0.0;
                                            Double desagioTotal_14 = 0.0;
                                            Double desagioTotal_30 = 0.0;
                                            Double desagioTotal_30_tarja = 0.0;

                                            Double parcela = valor1 / opTaxa1;

                                            for (int i = 1; i <= opTaxa1; i++) {
                                                desagioTotal_na_hora += parcela - (parcela / Math.pow(1 + taxa_parcelamento_na_hora, i));
                                                desagioTotal_14 += parcela - (parcela / Math.pow(1 + taxa_parcelamento_14, i));
                                                desagioTotal_30 += parcela - (parcela / Math.pow(1 + taxa_parcelamento_30, i));
                                                desagioTotal_30_tarja += parcela - (parcela / Math.pow(1 + taxa_parcelamento_30_tarja, i));

                                                valorPresenteTotal_na_hora += parcela / Math.pow(1 + taxa_parcelamento_na_hora, i);
                                                valorPresenteTotal_14 += parcela / Math.pow(1 + taxa_parcelamento_14, i);
                                                valorPresenteTotal_30 += parcela / Math.pow(1 + taxa_parcelamento_30, i);
                                                valorPresenteTotal_30_tarja += parcela / Math.pow(1 + taxa_parcelamento_30_tarja, i);
                                            }

                                            taxaParcelamentoTotal_na_hora = (desagioTotal_na_hora / valor1) * 100;
                                            taxaParcelamentoTotal_14 = (desagioTotal_14 / valor1) * 100;
                                            taxaParcelamentoTotal_30 = (desagioTotal_30 / valor1) * 100;
                                            taxaParcelamentoTotal_30_tarja = (desagioTotal_30_tarja / valor1) * 100;

                                            valorParcelamentoTotal_na_hora = valor1 - valorPresenteTotal_na_hora;
                                            valorParcelamentoTotal_14 = valor1 - valorPresenteTotal_14;
                                            valorParcelamentoTotal_30 = valor1 - valorPresenteTotal_30;
                                            valorParcelamentoTotal_30_tarja = valor1 - valorPresenteTotal_30_tarja;

                                            parcelaCliente = new BigDecimal(valor1 / opTaxa1);
                                            valorReceber_na_hora = new BigDecimal(valor1 - (valorIntermediacao_na_hora * 100) - valorParcelamentoTotal_na_hora);
                                            valorReceber_14 = new BigDecimal(valor1 - (valorIntermediacao_14 * 100) - valorParcelamentoTotal_14);
                                            valorReceber_30 = new BigDecimal(valor1 - (valorIntermediacao_30 * 100) - valorParcelamentoTotal_30);
                                            valorReceber_30_tarja = new BigDecimal(valor1 - (valorIntermediacao_30_tarja * 100) - valorParcelamentoTotal_30_tarja);

                                            textViewResultadosUsuario.setText("Valor a receber\n" +
                                                    "Na hora: " + colocarVirgula(String.valueOf(valorReceber_na_hora.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n14 dias:" + colocarVirgula(String.valueOf(valorReceber_14.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n30 dias:" + colocarVirgula(String.valueOf(valorReceber_30.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n30 dias Tarja:" + colocarVirgula(String.valueOf(valorReceber_30_tarja.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente.setScale(0, BigDecimal.ROUND_UP))));

                                        } else if (opTaxa1 == 1) {
                                            valorReceber_na_hora = new BigDecimal(valor1 - ((valor1 * (credito_a_vista_na_hora * 100)) / 100));
                                            valorReceber_14 = new BigDecimal(valor1 - ((valor1 * (credito_a_vista_14 * 100)) / 100));
                                            valorReceber_30 = new BigDecimal(valor1 - ((valor1 * (credito_a_vista_30 * 100)) / 100));
                                            valorReceber_30_tarja = new BigDecimal(valor1 - ((valor1 * (credito_a_vista_30_tarja * 100)) / 100));

                                            parcelaCliente = new BigDecimal(valor1 / opTaxa1);

                                            textViewResultadosUsuario.setText("Valor a receber\n" +
                                                    "Na hora: " + colocarVirgula(String.valueOf(valorReceber_na_hora.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n14 dias:" + colocarVirgula(String.valueOf(valorReceber_14.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n30 dias:" + colocarVirgula(String.valueOf(valorReceber_30.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n30 dias Tarja:" + colocarVirgula(String.valueOf(valorReceber_30_tarja.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente.setScale(0, BigDecimal.ROUND_UP))));

                                        }


                                    } else { // REPASSAR // TA OK
                                        Double valor1 = Double.parseDouble(unmask(valor));
                                        Double opTaxa1 = Double.parseDouble(unmask(opTaxa));

                                        Double taxaIntermediacao_na_hora = 0.0;
                                        Double taxaIntermediacao_14 = 0.0;
                                        Double taxaIntermediacao_30 = 0.0;
                                        Double taxaIntermediacao_30_tarja = 0.0;

                                        if (opTaxa1 > 1) {
                                            taxaIntermediacao_na_hora = credito_parcelado_na_hora;
                                            taxaIntermediacao_14 = credito_parcelado_14;
                                            taxaIntermediacao_30 = credito_parcelado_30;
                                            taxaIntermediacao_30_tarja = credito_parcelado_30_tarja;

                                            Double valorIntermediacao_na_hora = ((valor1 * (taxaIntermediacao_na_hora * 100)) / 100) / 100;
                                            Double valorIntermediacao_14 = ((valor1 * (taxaIntermediacao_14 * 100)) / 100) / 100;
                                            Double valorIntermediacao_30 = ((valor1 * (taxaIntermediacao_30 * 100)) / 100) / 100;
                                            Double valorIntermediacao_30_tarja = ((valor1 * (taxaIntermediacao_30_tarja * 100)) / 100) / 100;

                                            Double valorPresente_na_hora = (valor1 / opTaxa1) / Math.pow(1 + taxa_parcelamento_na_hora, opTaxa1); //valor dando certo
                                            Double valorPresente_14 = (valor1 / opTaxa1) / Math.pow(1 + taxa_parcelamento_14, opTaxa1); //valor dando certo
                                            Double valorPresente_30 = (valor1 / opTaxa1) / Math.pow(1 + taxa_parcelamento_30, opTaxa1); //valor dando certo
                                            Double valorPresente_30_tarja = (valor1 / opTaxa1) / Math.pow(1 + taxa_parcelamento_30_tarja, opTaxa1); //valor dando certo

                                            Double valorPresenteTotal_na_hora = 0.0;
                                            Double valorPresenteTotal_14 = 0.0;
                                            Double valorPresenteTotal_30 = 0.0;
                                            Double valorPresenteTotal_30_tarja = 0.0;

                                            Double desagioTotal_na_hora = 0.0;
                                            Double desagioTotal_14 = 0.0;
                                            Double desagioTotal_30 = 0.0;
                                            Double desagioTotal_30_tarja = 0.0;

                                            Double parcela = valor1 / opTaxa1;

                                            for (int i = 1; i <= opTaxa1; i++) {
                                                desagioTotal_na_hora += parcela - (parcela / Math.pow(1 + taxa_parcelamento_na_hora, i));
                                                desagioTotal_14 += parcela - (parcela / Math.pow(1 + taxa_parcelamento_14, i));
                                                desagioTotal_30 += parcela - (parcela / Math.pow(1 + taxa_parcelamento_30, i));
                                                desagioTotal_30_tarja += parcela - (parcela / Math.pow(1 + taxa_parcelamento_30_tarja, i));

                                                valorPresenteTotal_na_hora += parcela / Math.pow(1 + taxa_parcelamento_na_hora, i);
                                                valorPresenteTotal_14 += parcela / Math.pow(1 + taxa_parcelamento_14, i);
                                                valorPresenteTotal_30 += parcela / Math.pow(1 + taxa_parcelamento_30, i);
                                                valorPresenteTotal_30_tarja += parcela / Math.pow(1 + taxa_parcelamento_30_tarja, i);
                                            }

                                            taxaParcelamentoTotal_na_hora = (desagioTotal_na_hora / valor1) * 100;
                                            taxaParcelamentoTotal_14 = (desagioTotal_14 / valor1) * 100;
                                            taxaParcelamentoTotal_30 = (desagioTotal_30 / valor1) * 100;
                                            taxaParcelamentoTotal_30_tarja = (desagioTotal_30_tarja / valor1) * 100;

                                            valorParcelamentoTotal_na_hora = valor1 - valorPresenteTotal_na_hora;
                                            valorParcelamentoTotal_14 = valor1 - valorPresenteTotal_14;
                                            valorParcelamentoTotal_30 = valor1 - valorPresenteTotal_30;
                                            valorParcelamentoTotal_30_tarja = valor1 - valorPresenteTotal_30_tarja;

                                            Double jurosTotal$_na_hora = (valor1 - (valor1 - (valorIntermediacao_na_hora * 100) - valorParcelamentoTotal_na_hora));
                                            Double jurosTotal$_14 = (valor1 - (valor1 - (valorIntermediacao_14 * 100) - valorParcelamentoTotal_14));
                                            Double jurosTotal$_30 = (valor1 - (valor1 - (valorIntermediacao_30 * 100) - valorParcelamentoTotal_30));
                                            Double jurosTotal$_30_tarja = (valor1 - (valor1 - (valorIntermediacao_30_tarja * 100) - valorParcelamentoTotal_30_tarja));

                                            Double jurosTotalPorcentagem_na_hora = jurosTotal$_na_hora / valor1;
                                            Double jurosTotalPorcentagem_14 = jurosTotal$_14 / valor1;
                                            Double jurosTotalPorcentagem_30 = jurosTotal$_30 / valor1;
                                            Double jurosTotalPorcentagem_30_tarja = jurosTotal$_30_tarja / valor1;

                                            valorCobrar_na_hora = new BigDecimal((valor1 / (1 - jurosTotalPorcentagem_na_hora)) / opTaxa1);
                                            valorCobrar_14 = new BigDecimal((valor1 / (1 - jurosTotalPorcentagem_14)) / opTaxa1);
                                            valorCobrar_30 = new BigDecimal((valor1 / (1 - jurosTotalPorcentagem_30)) / opTaxa1);
                                            valorCobrar_30_tarja = new BigDecimal((valor1 / (1 - jurosTotalPorcentagem_30_tarja)) / opTaxa1);

                                            valorReceber_na_hora = new BigDecimal(valor1 / (1 - jurosTotalPorcentagem_na_hora));
                                            valorReceber_14 = new BigDecimal(valor1 / (1 - jurosTotalPorcentagem_14));
                                            valorReceber_30 = new BigDecimal(valor1 / (1 - jurosTotalPorcentagem_30));
                                            valorReceber_30_tarja = new BigDecimal(valor1 / (1 - jurosTotalPorcentagem_30_tarja));

                                            textViewResultadosUsuario.setText("Valor a cobrar\n" +
                                                    "Na hora: " + colocarVirgula(String.valueOf(valorReceber_na_hora.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n14 dias: " + colocarVirgula(String.valueOf(valorReceber_14.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n30 dias: " + colocarVirgula(String.valueOf(valorReceber_30.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n30 dias Tarja: " + colocarVirgula(String.valueOf(valorReceber_30_tarja.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(valorCobrar_na_hora.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(valorCobrar_14.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(valorCobrar_30.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(valorCobrar_30_tarja.setScale(0, BigDecimal.ROUND_UP))));
                                        }
                                    }
                                }

                            } else {
                                Log.d("TAG", "Documento não existe");
                            }

                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
    }

    public void mercadoPago(final String maquineta, final String valor, final String opTaxa, final String opParcela) {
        if (editResultado.getText().length() == 0) {
            Toast.makeText(this, "Informe um valor", Toast.LENGTH_SHORT).show();
            editResultado.requestFocus();
        } else {
            db.collection(maquineta).document(maquineta).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            if (documentSnapshot.exists()) {
                                Double debito_na_hora = documentSnapshot.getDouble(KEY_TAXA_DEBITO_NA_HORA);
                                Double debito_14 = documentSnapshot.getDouble(KEY_TAXA_DEBITO_14);
                                Double debito_30 = documentSnapshot.getDouble(KEY_TAXA_DEBITO_30);
                                Double credito_a_vista_na_hora = documentSnapshot.getDouble(KEY_CREDITO_A_VISTA_NA_HORA);
                                Double credito_a_vista_14 = documentSnapshot.getDouble(KEY_CREDITO_A_VISTA_14);
                                Double credito_a_vista_30 = documentSnapshot.getDouble(KEY_CREDITO_A_VISTA_30);
                                Double credito_parcelado_na_hora = documentSnapshot.getDouble(KEY_CREDITO_PARCELADO_NA_HORA);
                                Double credito_parcelado_14 = documentSnapshot.getDouble(KEY_CREDITO_PARCELADO_14);
                                Double credito_parcelado_30 = documentSnapshot.getDouble(KEY_CREDITO_PARCELADO_30);
                                Double[] taxa_parcelamento;
                                taxa_parcelamento = new Double[]{null, 0.0, 0.0409, 0.0541, 0.0670, 0.0796, 0.0920,
                                        0.1041, 0.1161, 0.1278, 0.1392, 0.1505, 0.1615};


                                if (opTaxa.equals("Debito")) {

                                    if (opParcela.equals("Assumir")) {
                                        Double valor1 = Double.parseDouble(unmask(valor.trim()));
                                        BigDecimal valorReceber_na_hora = new BigDecimal(valor1 - (valor1 * (debito_na_hora * 100)) / 100);
                                        BigDecimal valorReceber_14 = new BigDecimal(valor1 - (valor1 * (debito_14 * 100)) / 100);
                                        BigDecimal valorReceber_30 = new BigDecimal(valor1 - (valor1 * (debito_30 * 100)) / 100);

                                        BigDecimal parcelaCliente_na_hora = new BigDecimal(valor1);
                                        BigDecimal parcelaCliente_14 = new BigDecimal(valor1);
                                        BigDecimal parcelaCliente_30 = new BigDecimal(valor1);

                                        textViewResultadosUsuario.setText("Valor a receber\n" +
                                                "Débito na hora: " + colocarVirgula(String.valueOf(valorReceber_na_hora.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nDébito 14 dias: " + colocarVirgula(String.valueOf(valorReceber_14.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nDébito 30 dias: " + colocarVirgula(String.valueOf(valorReceber_30.setScale(0, BigDecimal.ROUND_UP))));

                                        textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_na_hora.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_14.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30.setScale(0, BigDecimal.ROUND_UP))));

                                    } else {

                                        Double valor1 = Double.parseDouble(unmask(valor).trim());
                                        BigDecimal valorCobrar_na_hora = new BigDecimal(valor1 / (1 - debito_na_hora));
                                        BigDecimal valorCobrar_14 = new BigDecimal(valor1 / (1 - debito_14));
                                        BigDecimal valorCobrar_30 = new BigDecimal(valor1 / (1 - debito_30));

                                        BigDecimal parcelaCliente_na_hora = new BigDecimal(valor1 / (1 - debito_na_hora));
                                        BigDecimal parcelaCliente_14 = new BigDecimal(valor1 / (1 - debito_14));
                                        BigDecimal parcelaCliente_30 = new BigDecimal(valor1 / (1 - debito_30));

                                        textViewResultadosUsuario.setText("Valor a cobrar\n" +
                                                "Débito na hora: " + colocarVirgula(String.valueOf(valorCobrar_na_hora.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nDébito 14 dias: " + colocarVirgula(String.valueOf(valorCobrar_14.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nDébito 30 dias: " + colocarVirgula(String.valueOf(valorCobrar_30.setScale(0, BigDecimal.ROUND_UP))));

                                        textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                colocarVirgula(String.valueOf(parcelaCliente_na_hora.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                colocarVirgula(String.valueOf(parcelaCliente_14.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                colocarVirgula(String.valueOf(parcelaCliente_30.setScale(0, BigDecimal.ROUND_UP))));
                                    }
                                } else if (opTaxa != "Debito") {

                                    if (opParcela.equals("Assumir")) { // CERTO
                                        Double valor1 = Double.parseDouble(unmask(valor));
                                        Double opTaxa1 = Double.parseDouble(unmask(opTaxa));
                                        int opTaxaInt = Integer.parseInt(unmask(opTaxa));

                                        if (opTaxa1 > 1) {
                                            BigDecimal parcelaCliente_na_hora = new BigDecimal(valor1 / opTaxa1);
                                            BigDecimal parcelaCliente_14 = new BigDecimal(valor1 / opTaxa1);
                                            BigDecimal parcelaCliente_30 = new BigDecimal(valor1 / opTaxa1);
                                            BigDecimal valorReceber_na_hora = new BigDecimal(valor1 - ((taxa_parcelamento[opTaxaInt] + credito_parcelado_na_hora) * valor1));
                                            BigDecimal valorReceber_14 = new BigDecimal(valor1 - ((taxa_parcelamento[opTaxaInt] + credito_parcelado_14) * valor1));
                                            BigDecimal valorReceber_30 = new BigDecimal(valor1 - ((taxa_parcelamento[opTaxaInt] + credito_parcelado_30) * valor1));

                                            textViewResultadosUsuario.setText("Valor a receber\n" +
                                                    "Na hora: " + colocarVirgula(String.valueOf(valorReceber_na_hora.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n14 dias:" + colocarVirgula(String.valueOf(valorReceber_14.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n30 dias:" + colocarVirgula(String.valueOf(valorReceber_30.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_na_hora.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_14.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30.setScale(0, BigDecimal.ROUND_UP))));
                                        } else {

                                            BigDecimal parcelaCliente_na_hora = new BigDecimal(valor1 / opTaxa1);
                                            BigDecimal parcelaCliente_14 = new BigDecimal(valor1 / opTaxa1);
                                            BigDecimal parcelaCliente_30 = new BigDecimal(valor1 / opTaxa1);

                                            BigDecimal valorReceber_na_hora = new BigDecimal(valor1 - (valor1 * (credito_a_vista_na_hora)));
                                            BigDecimal valorReceber_14 = new BigDecimal(valor1 - (valor1 * (credito_a_vista_14)));
                                            BigDecimal valorReceber_30 = new BigDecimal(valor1 - (valor1 * (credito_a_vista_30)));

                                            textViewResultadosUsuario.setText("Valor a receber\n" +
                                                    "Na hora: " + colocarVirgula(String.valueOf(valorReceber_na_hora.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n14 dias:" + colocarVirgula(String.valueOf(valorReceber_14.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n30 dias:" + colocarVirgula(String.valueOf(valorReceber_30.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_na_hora.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_14.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30.setScale(0, BigDecimal.ROUND_UP))));
                                        }


                                    } else { // REPASSAR // TA OK
                                        Double valor1 = Double.parseDouble(unmask(valor));
                                        Double opTaxa1 = Double.parseDouble(unmask(opTaxa));
                                        int opTaxaInt = Integer.parseInt(unmask(opTaxa));

                                        if (opTaxa1 > 1) {
                                            BigDecimal parcelaCliente_na_hora = new BigDecimal(valor1 / (1 - (credito_parcelado_na_hora + taxa_parcelamento[opTaxaInt])) / opTaxa1);
                                            BigDecimal parcelaCliente_14 = new BigDecimal(valor1 / (1 - (credito_parcelado_14 + taxa_parcelamento[opTaxaInt])) / opTaxa1);
                                            BigDecimal parcelaCliente_30 = new BigDecimal(valor1 / (1 - (credito_parcelado_30 + taxa_parcelamento[opTaxaInt])) / opTaxa1);

                                            BigDecimal valorCobrar_na_hora = new BigDecimal(valor1 / (1 - (credito_parcelado_na_hora + taxa_parcelamento[opTaxaInt])));
                                            BigDecimal valorCobrar_14 = new BigDecimal(valor1 / (1 - (credito_parcelado_14 + taxa_parcelamento[opTaxaInt])));
                                            BigDecimal valorCobrar_30 = new BigDecimal(valor1 / (1 - (credito_parcelado_30 + taxa_parcelamento[opTaxaInt])));

                                            textViewResultadosUsuario.setText("Valor a Cobrar\n" +
                                                    "Na hora: " + colocarVirgula(String.valueOf(valorCobrar_na_hora.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n14 dias:" + colocarVirgula(String.valueOf(valorCobrar_14.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n30 dias:" + colocarVirgula(String.valueOf(valorCobrar_30.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_na_hora.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_14.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30.setScale(0, BigDecimal.ROUND_UP))));
                                        } else {

                                            BigDecimal parcelaCliente_na_hora = new BigDecimal((valor1 / (1 - credito_a_vista_na_hora)) / opTaxa1);
                                            BigDecimal parcelaCliente_14 = new BigDecimal((valor1 / (1 - credito_a_vista_14)) / opTaxa1);
                                            BigDecimal parcelaCliente_30 = new BigDecimal((valor1 / (1 - credito_a_vista_30)) / opTaxa1);

                                            BigDecimal valorCobrar_na_hora = new BigDecimal(valor1 / (1 - credito_a_vista_na_hora));
                                            BigDecimal valorCobrar_14 = new BigDecimal(valor1 / (1 - credito_a_vista_14));
                                            BigDecimal valorCobrar_30 = new BigDecimal(valor1 / (1 - credito_a_vista_30));

                                            textViewResultadosUsuario.setText("Valor a Cobrar\n" +
                                                    "Na hora: " + colocarVirgula(String.valueOf(valorCobrar_na_hora.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n14 dias:" + colocarVirgula(String.valueOf(valorCobrar_14.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n30 dias:" + colocarVirgula(String.valueOf(valorCobrar_30.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_na_hora.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_14.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30.setScale(0, BigDecimal.ROUND_UP))));
                                        }
                                    }
                                }

                            } else {
                                Log.d("TAGG", "Documento não existe");
                            }

                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
    }

    public void ifood(final String maquineta, final String valor, final String opTaxa, final String opParcela) {
        if (editResultado.getText().length() == 0) {
            Toast.makeText(this, "Informe um valor", Toast.LENGTH_SHORT).show();
            editResultado.requestFocus();
        } else {
            db.collection(maquineta).document(maquineta).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            if (documentSnapshot.exists()) {
                                Double taxa_credito_a_vista_30_fat_menor_50 = documentSnapshot.getDouble(KEY_CREDITO_A_VISTA_30_FAT_MENOR_50);
                                Double taxa_credito_a_vista_2_fat_menor_50 = documentSnapshot.getDouble(KEY_CREDITO_A_VISTA_2_FAT_MENOR_50);
                                Double taxa_credito_a_vista_30_fat_maior_50 = documentSnapshot.getDouble(KEY_CREDITO_A_VISTA_30_FAT_MAIOR_50);
                                Double taxa_credito_a_vista_2_fat_maior_50 = documentSnapshot.getDouble(KEY_CREDITO_A_VISTA_2_FAT_MAIOR_50);

                                Double taxa_credito_parcelado_30_fat_menor_50 = documentSnapshot.getDouble(KEY_CREDITO_PARCELADO_30_FAT_MENOR_50);
                                Double taxa_credito_parcelado_2_fat_menor_50 = documentSnapshot.getDouble(KEY_CREDITO_PARCELADO_2_FAT_MENOR_50);
                                Double taxa_credito_parcelado_30_fat_maior_50 = documentSnapshot.getDouble(KEY_CREDITO_PARCELADO_30_FAT_MAIOR_50);
                                Double taxa_credito_parcelado_2_fat_maior_50 = documentSnapshot.getDouble(KEY_CREDITO_PARCELADO_2_FAT_MAIOR_50);

                                Double taxa_debito_fat_menor_50 = documentSnapshot.getDouble(KEY_TAXA_DEBITO_FAT_MENOR_50);
                                Double taxa_debito_fat_maior_50 = documentSnapshot.getDouble(KEY_TAXA_DEBITO_FAT_MAIOR_50);

                                Double taxa_de_parcelamento = documentSnapshot.getDouble(KEY_PARCELAMENTO);

                                if (opTaxa.equals("Debito")) {

                                    if (opParcela.equals("Assumir")) { // TA OK
                                        Double valor1 = Double.parseDouble(unmask(valor.trim()));
                                        BigDecimal valorReceber_30_fat_menor_50 = new BigDecimal(valor1 - (valor1 * (taxa_debito_fat_menor_50 * 100)) / 100);
                                        BigDecimal valorReceber_2_fat_menor_50 = new BigDecimal(valor1 - (valor1 * (taxa_debito_fat_menor_50 * 100)) / 100);
                                        BigDecimal valorReceber_30_fat_maior_50 = new BigDecimal(valor1 - (valor1 * (taxa_debito_fat_maior_50 * 100)) / 100);
                                        BigDecimal valorReceber_2_fat_maior_50 = new BigDecimal(valor1 - (valor1 * (taxa_debito_fat_maior_50 * 100)) / 100);

                                        textViewResultadosUsuario.setText("Valor a receber\n" +
                                                "Faturamento menor que 50 mil - 30 dias: " + colocarVirgula(String.valueOf(valorReceber_30_fat_menor_50.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nFaturamento menor que 50 mil - 2 dias: " + colocarVirgula(String.valueOf(valorReceber_2_fat_menor_50.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nFaturamento maior que 50 mil - 30 dias: " + colocarVirgula(String.valueOf(valorReceber_30_fat_maior_50.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nFaturamento maior que 50 mil - 2 dias: " + colocarVirgula(String.valueOf(valorReceber_2_fat_maior_50.setScale(0, BigDecimal.ROUND_UP))));

                                        textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                opTaxa + "\t" + colocarVirgula(String.valueOf(valor1)) + "\n" +
                                                opTaxa + "\t" + colocarVirgula(String.valueOf(valor1)) + "\n" +
                                                opTaxa + "\t" + colocarVirgula(String.valueOf(valor1)) + "\n" +
                                                opTaxa + "\t" + colocarVirgula(String.valueOf(valor1)));

                                    } else { // REPASSAR OK

                                        Double valor1 = Double.parseDouble(unmask(valor).trim());
                                        BigDecimal valorCobrar_30_fat_menor_50 = new BigDecimal(valor1 / (1 - taxa_debito_fat_menor_50));
                                        BigDecimal valorCobrar_2_fat_menor_50 = new BigDecimal(valor1 / (1 - taxa_debito_fat_menor_50));
                                        BigDecimal valorCobrar_30_fat_maior_50 = new BigDecimal(valor1 / (1 - taxa_debito_fat_maior_50));
                                        BigDecimal valorCobrar_2_fat_maior_50 = new BigDecimal(valor1 / (1 - taxa_debito_fat_maior_50));

                                        textViewResultadosUsuario.setText("Valor a cobrar\n" +
                                                "Faturamento menor que 50 mil - 30 dias: " + colocarVirgula(String.valueOf(valorCobrar_30_fat_menor_50.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nFaturamento menor que 50 mil - 2 dias: " + colocarVirgula(String.valueOf(valorCobrar_2_fat_menor_50.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nFaturamento maior que 50 mil - 30 dias: " + colocarVirgula(String.valueOf(valorCobrar_30_fat_maior_50.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nFaturamento maior que 50 mil - 2 dias: " + colocarVirgula(String.valueOf(valorCobrar_2_fat_maior_50.setScale(0, BigDecimal.ROUND_UP))));

                                        textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                colocarVirgula(String.valueOf(valorCobrar_30_fat_menor_50.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                colocarVirgula(String.valueOf(valorCobrar_2_fat_menor_50.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                colocarVirgula(String.valueOf(valorCobrar_30_fat_maior_50.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                colocarVirgula(String.valueOf(valorCobrar_2_fat_maior_50.setScale(0, BigDecimal.ROUND_UP))));
                                    }
                                } else if (opTaxa != "Debito") {

                                    if (opParcela.equals("Assumir")) { // OK
                                        Double valor1 = Double.parseDouble(unmask(valor));
                                        Double opTaxa1 = Double.parseDouble(unmask(opTaxa));

                                        if (opTaxa1 > 1) {

                                            BigDecimal valorReceber_30_fat_menor_50 = new BigDecimal(valor1 - (((taxa_de_parcelamento * (opTaxa1 - 1)) + taxa_credito_parcelado_30_fat_menor_50) * valor1));
                                            BigDecimal valorReceber_2_fat_menor_50 = new BigDecimal(valor1 - (((taxa_de_parcelamento * (opTaxa1 - 1)) + taxa_credito_parcelado_2_fat_menor_50) * valor1));
                                            BigDecimal valorReceber_30_fat_maior_50 = new BigDecimal(valor1 - (((taxa_de_parcelamento * (opTaxa1 - 1)) + taxa_credito_parcelado_30_fat_maior_50) * valor1));
                                            BigDecimal valorReceber_2_fat_maior_50 = new BigDecimal(valor1 - (((taxa_de_parcelamento * (opTaxa1 - 1)) + taxa_credito_parcelado_2_fat_maior_50) * valor1));

                                            BigDecimal parcelaCliente_30_fat_menor_50 = new BigDecimal((valor1 - (((taxa_de_parcelamento * (opTaxa1 - 1)) + taxa_credito_parcelado_30_fat_menor_50) * valor1)) / opTaxa1);
                                            BigDecimal parcelaCliente_2_fat_menor_50 = new BigDecimal((valor1 - (((taxa_de_parcelamento * (opTaxa1 - 1)) + taxa_credito_parcelado_2_fat_menor_50) * valor1)) / opTaxa1);
                                            BigDecimal parcelaCliente_30_fat_maior_50 = new BigDecimal((valor1 - (((taxa_de_parcelamento * (opTaxa1 - 1)) + taxa_credito_parcelado_30_fat_maior_50) * valor1)) / opTaxa1);
                                            BigDecimal parcelaCliente_2_fat_maior_50 = new BigDecimal((valor1 - (((taxa_de_parcelamento * (opTaxa1 - 1)) + taxa_credito_parcelado_2_fat_maior_50) * valor1)) / opTaxa1);

                                            textViewResultadosUsuario.setText("Valor a receber\n" +
                                                    "Faturamento menor que 50 mil - 30 dias: " + colocarVirgula(String.valueOf(valorReceber_30_fat_menor_50.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nFaturamento menor que 50 mil - 2 dias: " + colocarVirgula(String.valueOf(valorReceber_2_fat_menor_50.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nFaturamento maior que 50 mil - 30 dias: " + colocarVirgula(String.valueOf(valorReceber_30_fat_maior_50.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nFaturamento maior que 50 mil - 2 dias: " + colocarVirgula(String.valueOf(valorReceber_2_fat_maior_50.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30_fat_menor_50.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_fat_menor_50.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30_fat_maior_50.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_fat_maior_50.setScale(0, BigDecimal.ROUND_UP))));
                                        } else {
                                            BigDecimal valorReceber_30_fat_menor_50 = new BigDecimal(valor1 - (valor1 * (taxa_credito_a_vista_30_fat_menor_50)));
                                            BigDecimal valorReceber_2_fat_menor_50 = new BigDecimal(valor1 - (valor1 * (taxa_credito_a_vista_2_fat_menor_50)));
                                            BigDecimal valorReceber_30_fat_maior_50 = new BigDecimal(valor1 - (valor1 * (taxa_credito_a_vista_30_fat_maior_50)));
                                            BigDecimal valorReceber_2_fat_maior_50 = new BigDecimal(valor1 - (valor1 * (taxa_credito_a_vista_2_fat_maior_50)));

                                            BigDecimal parcelaCliente_30_fat_menor_50 = new BigDecimal(valor1);
                                            BigDecimal parcelaCliente_2_fat_menor_50 = new BigDecimal(valor1);
                                            BigDecimal parcelaCliente_30_fat_maior_50 = new BigDecimal(valor1);
                                            BigDecimal parcelaCliente_2_fat_maior_50 = new BigDecimal(valor1);

                                            textViewResultadosUsuario.setText("Valor a receber\n" +
                                                    "Faturamento menor que 50 mil - 30 dias: " + colocarVirgula(String.valueOf(valorReceber_30_fat_menor_50.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nFaturamento menor que 50 mil - 2 dias: " + colocarVirgula(String.valueOf(valorReceber_2_fat_menor_50.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nFaturamento maior que 50 mil - 30 dias: " + colocarVirgula(String.valueOf(valorReceber_30_fat_maior_50.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nFaturamento maior que 50 mil - 2 dias: " + colocarVirgula(String.valueOf(valorReceber_2_fat_maior_50.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30_fat_menor_50.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_fat_menor_50.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30_fat_maior_50.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_fat_maior_50.setScale(0, BigDecimal.ROUND_UP))));
                                        }

                                    } else { // REPASSAR // TA OK
                                        Double valor1 = Double.parseDouble(unmask(valor));
                                        Double opTaxa1 = Double.parseDouble(unmask(opTaxa));

                                        if (opTaxa1 > 1) {

                                            BigDecimal valorCobrar_30_fat_menor_50 = new BigDecimal(valor1 / (1 - (taxa_credito_parcelado_30_fat_menor_50 + (taxa_de_parcelamento * (opTaxa1 - 1)))));
                                            BigDecimal valorCobrar_2_fat_menor_50 = new BigDecimal(valor1 / (1 - (taxa_credito_parcelado_2_fat_menor_50 + (taxa_de_parcelamento * (opTaxa1 - 1)))));
                                            BigDecimal valorCobrar_30_fat_maior_50 = new BigDecimal(valor1 / (1 - (taxa_credito_parcelado_30_fat_maior_50 + (taxa_de_parcelamento * (opTaxa1 - 1)))));
                                            BigDecimal valorCobrar_2_fat_maior_50 = new BigDecimal(valor1 / (1 - (taxa_credito_parcelado_2_fat_maior_50 + (taxa_de_parcelamento * (opTaxa1 - 1)))));

                                            BigDecimal parcelaCliente_30_fat_menor_50 = new BigDecimal(valor1 / (1 - (taxa_credito_parcelado_30_fat_menor_50 + (taxa_de_parcelamento * (opTaxa1 - 1)))) / opTaxa1);
                                            BigDecimal parcelaCliente_2_fat_menor_50 = new BigDecimal(valor1 / (1 - (taxa_credito_parcelado_2_fat_menor_50 + (taxa_de_parcelamento * (opTaxa1 - 1)))) / opTaxa1);
                                            BigDecimal parcelaCliente_30_fat_maior_50 = new BigDecimal(valor1 / (1 - (taxa_credito_parcelado_30_fat_maior_50 + (taxa_de_parcelamento * (opTaxa1 - 1)))) / opTaxa1);
                                            BigDecimal parcelaCliente_2_fat_maior_50 = new BigDecimal(valor1 / (1 - (taxa_credito_parcelado_2_fat_maior_50 + (taxa_de_parcelamento * (opTaxa1 - 1)))) / opTaxa1);

                                            textViewResultadosUsuario.setText("Valor a receber\n" +
                                                    "Faturamento menor que 50 mil - 30 dias: " + colocarVirgula(String.valueOf(valorCobrar_30_fat_menor_50.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nFaturamento menor que 50 mil - 2 dias: " + colocarVirgula(String.valueOf(valorCobrar_2_fat_menor_50.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nFaturamento maior que 50 mil - 30 dias: " + colocarVirgula(String.valueOf(valorCobrar_30_fat_maior_50.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nFaturamento maior que 50 mil - 2 dias: " + colocarVirgula(String.valueOf(valorCobrar_2_fat_maior_50.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30_fat_menor_50.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_fat_menor_50.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30_fat_maior_50.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_fat_maior_50.setScale(0, BigDecimal.ROUND_UP))));
                                        } else {
                                            BigDecimal valorCobrar_30_fat_menor_50 = new BigDecimal(valor1 / (1 - taxa_credito_a_vista_30_fat_menor_50));
                                            BigDecimal valorCobrar_2_fat_menor_50 = new BigDecimal(valor1 / (1 - taxa_credito_a_vista_2_fat_menor_50));
                                            BigDecimal valorCobrar_30_fat_maior_50 = new BigDecimal(valor1 / (1 - taxa_credito_a_vista_30_fat_maior_50));
                                            BigDecimal valorCobrar_2_fat_maior_50 = new BigDecimal(valor1 / (1 - taxa_credito_a_vista_2_fat_maior_50));

                                            BigDecimal parcelaCliente_30_fat_menor_50 = new BigDecimal(valor1);
                                            BigDecimal parcelaCliente_2_fat_menor_50 = new BigDecimal(valor1);
                                            BigDecimal parcelaCliente_30_fat_maior_50 = new BigDecimal(valor1);
                                            BigDecimal parcelaCliente_2_fat_maior_50 = new BigDecimal(valor1);

                                            textViewResultadosUsuario.setText("Valor a receber\n" +
                                                    "Faturamento menor que 50 mil - 30 dias: " + colocarVirgula(String.valueOf(valorCobrar_30_fat_menor_50.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nFaturamento menor que 50 mil - 2 dias: " + colocarVirgula(String.valueOf(valorCobrar_2_fat_menor_50.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nFaturamento maior que 50 mil - 30 dias: " + colocarVirgula(String.valueOf(valorCobrar_30_fat_maior_50.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nFaturamento maior que 50 mil - 2 dias: " + colocarVirgula(String.valueOf(valorCobrar_2_fat_maior_50.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30_fat_menor_50.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_fat_menor_50.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30_fat_maior_50.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_fat_maior_50.setScale(0, BigDecimal.ROUND_UP))));
                                        }
                                    }
                                }

                            } else {
                                Log.d("TAGG", "Documento não existe");
                            }

                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
    }

    public void bevimais(final String maquineta, final String valor, final String opTaxa, final String opParcela) {
        if (editResultado.getText().length() == 0) {
            Toast.makeText(this, "Informe um valor", Toast.LENGTH_SHORT).show();
            editResultado.requestFocus();
        } else {
            db.collection(maquineta).document(maquineta).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            if (documentSnapshot.exists()) {
                                Double taxa_credito_a_vista_2_pf = documentSnapshot.getDouble(KEY_CREDITO_A_VISTA_2_PF);
                                Double taxa_credito_a_vista_30_pf = documentSnapshot.getDouble(KEY_CREDITO_A_VISTA_30_PF);
                                Double taxa_credito_a_vista_2_pj = documentSnapshot.getDouble(KEY_CREDITO_A_VISTA_2_PJ);
                                Double taxa_credito_a_vista_30_pj = documentSnapshot.getDouble(KEY_CREDITO_A_VISTA_30_PJ);

                                Double taxa_credito_parcelado_2_pf = documentSnapshot.getDouble(KEY_CREDITO_PARCELADO_2_PF);
                                Double taxa_credito_parcelado_30_pf = documentSnapshot.getDouble(KEY_CREDITO_PARCELADO_30_PF);
                                Double taxa_credito_parcelado_2_pj = documentSnapshot.getDouble(KEY_CREDITO_PARCELADO_2_PJ);
                                Double taxa_credito_parcelado_30_pj = documentSnapshot.getDouble(KEY_CREDITO_PARCELADO_30_PJ);

                                Double taxa_debito_2_pf = documentSnapshot.getDouble(KEY_TAXA_DEBITO_2_PF);
                                Double taxa_debito_30_pf = documentSnapshot.getDouble(KEY_TAXA_DEBITO_30_PF);
                                Double taxa_debito_2_pj = documentSnapshot.getDouble(KEY_TAXA_DEBITO_2_PJ);
                                Double taxa_debito_30_pj = documentSnapshot.getDouble(KEY_TAXA_DEBITO_30_PJ);

                                Double taxa_de_parcelamento_2_pf = documentSnapshot.getDouble(KEY_PARCELAMENTO_2_PF);
                                Double taxa_de_parcelamento_30_pf = documentSnapshot.getDouble(KEY_PARCELAMENTO_30_PF);
                                Double taxa_de_parcelamento_2_pj = documentSnapshot.getDouble(KEY_PARCELAMENTO_2_PJ);
                                Double taxa_de_parcelamento_30_pj = documentSnapshot.getDouble(KEY_PARCELAMENTO_30_PJ);

                                if (opTaxa.equals("Debito")) {

                                    if (opParcela.equals("Assumir")) { // TA OK
                                        Double valor1 = Double.parseDouble(unmask(valor.trim()));
                                        BigDecimal valorReceber_2_pf = new BigDecimal(valor1 - (valor1 * (taxa_debito_2_pf * 100)) / 100);
                                        BigDecimal valorReceber_30_pf = new BigDecimal(valor1 - (valor1 * (taxa_debito_30_pf * 100)) / 100);
                                        BigDecimal valorReceber_2_pj = new BigDecimal(valor1 - (valor1 * (taxa_debito_2_pj * 100)) / 100);
                                        BigDecimal valorReceber_30_pj = new BigDecimal(valor1 - (valor1 * (taxa_debito_30_pj * 100)) / 100);

                                        BigDecimal parcelaCliente_2_pf = new BigDecimal(valor1);
                                        BigDecimal parcelaCliente_30_pf = new BigDecimal(valor1);
                                        BigDecimal parcelaCliente_2_pj = new BigDecimal(valor1);
                                        BigDecimal parcelaCliente_30_pj = new BigDecimal(valor1);

                                        textViewResultadosUsuario.setText("Valor a receber\n" +
                                                "2 Dias - Antecipado - Pessoa Jurídica: " + colocarVirgula(String.valueOf(valorReceber_2_pj.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\n30 Dias - Antecipado - Pessoa Jurídica: " + colocarVirgula(String.valueOf(valorReceber_30_pj.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\n2 Dias - Parcelado - Pessoa Física: " + colocarVirgula(String.valueOf(valorReceber_2_pf.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\n30 Dias - Parcelado - Pessoa Física: " + colocarVirgula(String.valueOf(valorReceber_30_pf.setScale(0, BigDecimal.ROUND_UP))));

                                        textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_pf.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30_pf.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_pj.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30_pj.setScale(0, BigDecimal.ROUND_UP))));

                                    } else { // REPASSAR OK

                                        Double valor1 = Double.parseDouble(unmask(valor).trim());
                                        BigDecimal valorCobrar_2_pf = new BigDecimal(valor1 / (1 - taxa_debito_2_pf));
                                        BigDecimal valorCobrar_30_pf = new BigDecimal(valor1 / (1 - taxa_debito_30_pf));
                                        BigDecimal valorCobrar_2_pj = new BigDecimal(valor1 / (1 - taxa_debito_2_pj));
                                        BigDecimal valorCobrar_30_pj = new BigDecimal(valor1 / (1 - taxa_debito_30_pj));

                                        textViewResultadosUsuario.setText("Valor a cobrar\n" +
                                                "2 Dias - Antecipado - Pessoa Jurídica: " + colocarVirgula(String.valueOf(valorCobrar_2_pf.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\n30 Dias - Parcelado - Pessoa Jurídica: " + colocarVirgula(String.valueOf(valorCobrar_30_pf.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\n2 Dias - Antecipado - Pessoa Física: " + colocarVirgula(String.valueOf(valorCobrar_2_pj.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\n30 Dias - Parcelado - Pessoa Física: " + colocarVirgula(String.valueOf(valorCobrar_30_pj.setScale(0, BigDecimal.ROUND_UP))));

                                        textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                colocarVirgula(String.valueOf(valorCobrar_2_pf.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                colocarVirgula(String.valueOf(valorCobrar_30_pf.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                colocarVirgula(String.valueOf(valorCobrar_2_pj.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                colocarVirgula(String.valueOf(valorCobrar_30_pj.setScale(0, BigDecimal.ROUND_UP))));
                                    }
                                } else if (opTaxa != "Debito") {

                                    if (opParcela.equals("Assumir")) { // OK
                                        Double valor1 = Double.parseDouble(unmask(valor));
                                        Double opTaxa1 = Double.parseDouble(unmask(opTaxa));

                                        if (opTaxa1 > 1) {

                                            BigDecimal valorReceber_2_pf = new BigDecimal(valor1 - (((taxa_de_parcelamento_2_pf * (opTaxa1)) + taxa_credito_parcelado_2_pf) * valor1));
                                            BigDecimal valorReceber_30_pf = new BigDecimal(valor1 - (((taxa_de_parcelamento_30_pf * (opTaxa1)) + taxa_credito_parcelado_30_pf) * valor1));
                                            BigDecimal valorReceber_2_pj = new BigDecimal(valor1 - (((taxa_de_parcelamento_2_pj * (opTaxa1)) + taxa_credito_parcelado_2_pj) * valor1));
                                            BigDecimal valorReceber_30_pj = new BigDecimal(valor1 - (((taxa_de_parcelamento_30_pj * (opTaxa1)) + taxa_credito_parcelado_30_pj) * valor1));

                                            BigDecimal parcelaCliente_2_pf = new BigDecimal(valor1 / opTaxa1);
                                            BigDecimal parcelaCliente_30_pf = new BigDecimal(valor1 / opTaxa1);
                                            BigDecimal parcelaCliente_2_pj = new BigDecimal(valor1 / opTaxa1);
                                            BigDecimal parcelaCliente_30_pj = new BigDecimal(valor1 / opTaxa1);

                                            textViewResultadosUsuario.setText("Valor a receber\n" +
                                                    "2 Dias - Antecipado - Pessoa Jurídica: " + colocarVirgula(String.valueOf(valorReceber_2_pj.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n30 Dias - Parcelado - Pessoa Jurídica: " + colocarVirgula(String.valueOf(valorReceber_30_pj.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n2 Dias - Antecipado - Pessoa Física: " + colocarVirgula(String.valueOf(valorReceber_2_pf.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n30 Dias - Parcelado - Pessoa Física: " + colocarVirgula(String.valueOf(valorReceber_30_pf.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_pf.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30_pf.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_pj.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30_pj.setScale(0, BigDecimal.ROUND_UP))));
                                        } else {
                                            BigDecimal valorReceber_2_pf = new BigDecimal(valor1 - (valor1 * (taxa_credito_a_vista_2_pf)));
                                            BigDecimal valorReceber_30_pf = new BigDecimal(valor1 - (valor1 * (taxa_credito_a_vista_30_pf)));
                                            BigDecimal valorReceber_2_pj = new BigDecimal(valor1 - (valor1 * (taxa_credito_a_vista_2_pj)));
                                            BigDecimal valorReceber_30_pj = new BigDecimal(valor1 - (valor1 * (taxa_credito_a_vista_30_pj)));

                                            BigDecimal parcelaCliente_2_pf = new BigDecimal(valor1);
                                            BigDecimal parcelaCliente_30_pf = new BigDecimal(valor1);
                                            BigDecimal parcelaCliente_2_pj = new BigDecimal(valor1);
                                            BigDecimal parcelaCliente_30_pj = new BigDecimal(valor1);

                                            textViewResultadosUsuario.setText("Valor a receber\n" +
                                                    "2 Dias - Antecipado - Pessoa Jurídica: " + colocarVirgula(String.valueOf(valorReceber_2_pj.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n30 Dias - Parcelado - Pessoa Jurídica: " + colocarVirgula(String.valueOf(valorReceber_30_pj.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n2 Dias - Antecipado - Pessoa Física: " + colocarVirgula(String.valueOf(valorReceber_2_pf.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n30 Dias - Parcelado - Pessoa Física: " + colocarVirgula(String.valueOf(valorReceber_30_pf.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_pf.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30_pf.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_pj.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30_pj.setScale(0, BigDecimal.ROUND_UP))));
                                        }

                                    } else { // REPASSAR // TA OK
                                        Double valor1 = Double.parseDouble(unmask(valor));
                                        Double opTaxa1 = Double.parseDouble(unmask(opTaxa));

                                        if (opTaxa1 > 1) {

                                            BigDecimal valorCobrar_2_pf = new BigDecimal(valor1 / (1 - (taxa_credito_parcelado_2_pf + (taxa_de_parcelamento_2_pf * (opTaxa1)))));
                                            BigDecimal valorCobrar_30_pf = new BigDecimal(valor1 / (1 - (taxa_credito_parcelado_30_pf + (taxa_de_parcelamento_30_pf * (opTaxa1)))));
                                            BigDecimal valorCobrar_2_pj = new BigDecimal(valor1 / (1 - (taxa_credito_parcelado_2_pj + (taxa_de_parcelamento_2_pj * (opTaxa1)))));
                                            BigDecimal valorCobrar_30_pj = new BigDecimal(valor1 / (1 - (taxa_credito_parcelado_30_pj + (taxa_de_parcelamento_30_pj * (opTaxa1)))));

                                            BigDecimal parcelaCliente_2_pf = new BigDecimal(valor1 / (1 - (taxa_credito_parcelado_2_pf + (taxa_de_parcelamento_2_pf * (opTaxa1)))) / opTaxa1);
                                            BigDecimal parcelaCliente_30_pf = new BigDecimal(valor1 / (1 - (taxa_credito_parcelado_30_pf + (taxa_de_parcelamento_30_pf * (opTaxa1)))) / opTaxa1);
                                            BigDecimal parcelaCliente_2_pj = new BigDecimal(valor1 / (1 - (taxa_credito_parcelado_2_pj + (taxa_de_parcelamento_2_pj * (opTaxa1)))) / opTaxa1);
                                            BigDecimal parcelaCliente_30_pj = new BigDecimal(valor1 / (1 - (taxa_credito_parcelado_30_pj + (taxa_de_parcelamento_30_pj * (opTaxa1)))) / opTaxa1);

                                            textViewResultadosUsuario.setText("Valor a cobrar\n" +
                                                    "2 Dias - Antecipado - Pessoa Jurídica: " + colocarVirgula(String.valueOf(valorCobrar_2_pj.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n30 Dias - Parcelado - Pessoa Jurídica: " + colocarVirgula(String.valueOf(valorCobrar_30_pj.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n2 Dias - Antecipado - Pessoa Física: " + colocarVirgula(String.valueOf(valorCobrar_2_pf.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n30 Dias - Parcelado - Pessoa Física: " + colocarVirgula(String.valueOf(valorCobrar_30_pf.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_pj.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30_pj.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_pf.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30_pf.setScale(0, BigDecimal.ROUND_UP))));
                                        } else {
                                            BigDecimal valorCobrar_2_pf = new BigDecimal(valor1 / (1 - taxa_credito_a_vista_2_pf));
                                            BigDecimal valorCobrar_30_pf = new BigDecimal(valor1 / (1 - taxa_credito_a_vista_30_pf));
                                            BigDecimal valorCobrar_2_pj = new BigDecimal(valor1 / (1 - taxa_credito_a_vista_2_pj));
                                            BigDecimal valorCobrar_30_pj = new BigDecimal(valor1 / (1 - taxa_credito_a_vista_30_pj));

                                            BigDecimal parcelaCliente_2_pf = new BigDecimal(valor1 / (1 - taxa_credito_a_vista_2_pf));
                                            BigDecimal parcelaCliente_30_pf = new BigDecimal(valor1 / (1 - taxa_credito_a_vista_30_pf));
                                            BigDecimal parcelaCliente_2_pj = new BigDecimal(valor1 / (1 - taxa_credito_a_vista_2_pj));
                                            BigDecimal parcelaCliente_30_pj = new BigDecimal(valor1 / (1 - taxa_credito_a_vista_30_pj));

                                            textViewResultadosUsuario.setText("Valor a cobrar\n" +
                                                    "2 Dias - Antecipado - Pessoa Jurídica: " + colocarVirgula(String.valueOf(valorCobrar_2_pj.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n30 Dias - Parcelado - Pessoa Jurídica: " + colocarVirgula(String.valueOf(valorCobrar_30_pj.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n2 Dias - Antecipado - Pessoa Física: " + colocarVirgula(String.valueOf(valorCobrar_2_pf.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\n30 Dias - Parcelado - Pessoa Física: " + colocarVirgula(String.valueOf(valorCobrar_30_pf.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_pj.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30_pj.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_pf.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_30_pf.setScale(0, BigDecimal.ROUND_UP))));
                                        }
                                    }
                                }

                            } else {
                                Log.d("TAGG", "Documento não existe");
                            }

                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
    }

    public void bevi(final String maquineta, final String valor, final String opTaxa, final String opParcela) {
        if (editResultado.getText().length() == 0) {
            Toast.makeText(this, "Informe um valor", Toast.LENGTH_SHORT).show();
            editResultado.requestFocus();
        } else {
            db.collection(maquineta).document(maquineta).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            if (documentSnapshot.exists()) {
                                Double taxa_credito_a_vista_2_pf = documentSnapshot.getDouble(KEY_CREDITO_A_VISTA_2_PF);
                                Double taxa_credito_a_vista_2_pj = documentSnapshot.getDouble(KEY_CREDITO_A_VISTA_2_PJ);

                                Double taxa_credito_parcelado_2_pf = documentSnapshot.getDouble(KEY_CREDITO_PARCELADO_2_PF);
                                Double taxa_credito_parcelado_2_pj = documentSnapshot.getDouble(KEY_CREDITO_PARCELADO_2_PJ);

                                Double taxa_debito_2_pf = documentSnapshot.getDouble(KEY_TAXA_DEBITO_2_PF);
                                Double taxa_debito_2_pj = documentSnapshot.getDouble(KEY_TAXA_DEBITO_2_PJ);

                                Double taxa_de_parcelamento_2_pf = documentSnapshot.getDouble(KEY_PARCELAMENTO_2_PF);
                                Double taxa_de_parcelamento_2_pj = documentSnapshot.getDouble(KEY_PARCELAMENTO_2_PJ);

                                if (opTaxa.equals("Debito")) {

                                    if (opParcela.equals("Assumir")) { // TA OK
                                        Double valor1 = Double.parseDouble(unmask(valor.trim()));
                                        BigDecimal valorReceber_2_pf = new BigDecimal(valor1 - (valor1 * (taxa_debito_2_pf * 100)) / 100);
                                        BigDecimal valorReceber_2_pj = new BigDecimal(valor1 - (valor1 * (taxa_debito_2_pj * 100)) / 100);

                                        BigDecimal parcelaCliente_2_pf = new BigDecimal(valor1);
                                        BigDecimal parcelaCliente_2_pj = new BigDecimal(valor1);

                                        textViewResultadosUsuario.setText("Valor a receber\n" +
                                                "Pessoa Jurídica: " + colocarVirgula(String.valueOf(valorReceber_2_pj.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nPessoa Física: " + colocarVirgula(String.valueOf(valorReceber_2_pf.setScale(0, BigDecimal.ROUND_UP))));

                                        textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_pf.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_pj.setScale(0, BigDecimal.ROUND_UP))));

                                    } else { // REPASSAR OK

                                        Double valor1 = Double.parseDouble(unmask(valor).trim());
                                        BigDecimal valorCobrar_2_pf = new BigDecimal(valor1 / (1 - taxa_debito_2_pf));
                                        BigDecimal valorCobrar_2_pj = new BigDecimal(valor1 / (1 - taxa_debito_2_pj));

                                        textViewResultadosUsuario.setText("Valor a cobrar\n" +
                                                "Pessoa Jurídica: " + colocarVirgula(String.valueOf(valorCobrar_2_pf.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nPessoa Física: " + colocarVirgula(String.valueOf(valorCobrar_2_pj.setScale(0, BigDecimal.ROUND_UP))));

                                        textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                colocarVirgula(String.valueOf(valorCobrar_2_pf.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                colocarVirgula(String.valueOf(valorCobrar_2_pj.setScale(0, BigDecimal.ROUND_UP))));
                                    }
                                } else if (opTaxa != "Debito") {

                                    if (opParcela.equals("Assumir")) { // OK
                                        Double valor1 = Double.parseDouble(unmask(valor));
                                        Double opTaxa1 = Double.parseDouble(unmask(opTaxa));

                                        if (opTaxa1 > 1) {

                                            BigDecimal valorReceber_2_pf = new BigDecimal(valor1 - (((taxa_de_parcelamento_2_pf * (opTaxa1)) + taxa_credito_parcelado_2_pf) * valor1));
                                            BigDecimal valorReceber_2_pj = new BigDecimal(valor1 - (((taxa_de_parcelamento_2_pj * (opTaxa1)) + taxa_credito_parcelado_2_pj) * valor1));

                                            BigDecimal parcelaCliente_2_pf = new BigDecimal(valor1 / opTaxa1);
                                            BigDecimal parcelaCliente_2_pj = new BigDecimal(valor1 / opTaxa1);

                                            textViewResultadosUsuario.setText("Valor a receber\n" +
                                                    "Pessoa Jurídica: " + colocarVirgula(String.valueOf(valorReceber_2_pj.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nPessoa Física: " + colocarVirgula(String.valueOf(valorReceber_2_pf.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_pf.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_pj.setScale(0, BigDecimal.ROUND_UP))));
                                        } else {
                                            BigDecimal valorReceber_2_pf = new BigDecimal(valor1 - (valor1 * (taxa_credito_a_vista_2_pf)));
                                            BigDecimal valorReceber_2_pj = new BigDecimal(valor1 - (valor1 * (taxa_credito_a_vista_2_pj)));

                                            BigDecimal parcelaCliente_2_pf = new BigDecimal(valor1);
                                            BigDecimal parcelaCliente_2_pj = new BigDecimal(valor1);

                                            textViewResultadosUsuario.setText("Valor a receber\n" +
                                                    "Pessoa Jurídica: " + colocarVirgula(String.valueOf(valorReceber_2_pj.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nPessoa Física: " + colocarVirgula(String.valueOf(valorReceber_2_pf.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_pf.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_pj.setScale(0, BigDecimal.ROUND_UP))));
                                        }

                                    } else { // REPASSAR // TA OK
                                        Double valor1 = Double.parseDouble(unmask(valor));
                                        Double opTaxa1 = Double.parseDouble(unmask(opTaxa));

                                        if (opTaxa1 > 1) {

                                            BigDecimal valorCobrar_2_pf = new BigDecimal(valor1 / (1 - (taxa_credito_parcelado_2_pf + (taxa_de_parcelamento_2_pf * (opTaxa1)))));
                                            BigDecimal valorCobrar_2_pj = new BigDecimal(valor1 / (1 - (taxa_credito_parcelado_2_pj + (taxa_de_parcelamento_2_pj * (opTaxa1)))));

                                            BigDecimal parcelaCliente_2_pf = new BigDecimal(valor1 / (1 - (taxa_credito_parcelado_2_pf + (taxa_de_parcelamento_2_pf * (opTaxa1)))) / opTaxa1);
                                            BigDecimal parcelaCliente_2_pj = new BigDecimal(valor1 / (1 - (taxa_credito_parcelado_2_pj + (taxa_de_parcelamento_2_pj * (opTaxa1)))) / opTaxa1);

                                            textViewResultadosUsuario.setText("Valor a cobrar\n" +
                                                    "Pessoa Jurídica: " + colocarVirgula(String.valueOf(valorCobrar_2_pj.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nPessoa Física: " + colocarVirgula(String.valueOf(valorCobrar_2_pf.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_pj.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_pf.setScale(0, BigDecimal.ROUND_UP))));
                                        } else {
                                            BigDecimal valorCobrar_2_pf = new BigDecimal(valor1 / (1 - taxa_credito_a_vista_2_pf));
                                            BigDecimal valorCobrar_2_pj = new BigDecimal(valor1 / (1 - taxa_credito_a_vista_2_pj));

                                            BigDecimal parcelaCliente_2_pf = new BigDecimal(valor1 / (1 - taxa_credito_a_vista_2_pf));
                                            BigDecimal parcelaCliente_2_pj = new BigDecimal(valor1 / (1 - taxa_credito_a_vista_2_pj));

                                            textViewResultadosUsuario.setText("Valor a cobrar\n" +
                                                    "Pessoa Jurídica: " + colocarVirgula(String.valueOf(valorCobrar_2_pj.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nPessoa Física: " + colocarVirgula(String.valueOf(valorCobrar_2_pf.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_pj.setScale(0, BigDecimal.ROUND_UP))) + " \n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_2_pf.setScale(0, BigDecimal.ROUND_UP))));
                                        }
                                    }
                                }

                            } else {
                                Log.d("TAGG", "Documento não existe");

                            }

                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
    }

    public void starpay(final String maquineta, final String valor, final String opTaxa, final String opParcela) {
        if (editResultado.getText().length() == 0) {
            Toast.makeText(this, "Informe um valor", Toast.LENGTH_SHORT).show();
            editResultado.requestFocus();
        } else {
            db.collection(maquineta).document(maquineta).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            if (documentSnapshot.exists()) {
                                Double debito = documentSnapshot.getDouble(KEY_TAXA_DEBITO);
                                Double credito_a_vista = documentSnapshot.getDouble(KEY_CREDITO_A_VISTA);
                                Double credito_a_vista_veloz = documentSnapshot.getDouble(KEY_CREDITO_A_VISTA_VELOZ);
                                Double credito_a_vista_controle = documentSnapshot.getDouble(KEY_CREDITO_A_VISTA_CONTROLE);

                                Double taxa_de_parcelamento_2_6 = documentSnapshot.getDouble(KEY_PARCELAMENTO_2_6);
                                Double taxa_de_parcelamento_7_12 = documentSnapshot.getDouble(KEY_PARCELAMENTO_7_12);

                                Double[] taxa_veloz_antecipado;
                                taxa_veloz_antecipado = new Double[]{null, 0.03187, 0.0491, 0.0669, 0.0855, 0.1050, 0.1254};

                                Double[] taxa_controle_antecipado;
                                taxa_controle_antecipado = new Double[]{null, 0.04297, 0.06674, 0.09166, 0.11815, 0.14638, 0.17658};


                                if (opTaxa.equals("Debito")) {

                                    if (opParcela.equals("Assumir")) {
                                        Double valor1 = Double.parseDouble(unmask(valor.trim()));
                                        BigDecimal valorReceber_antecipado = new BigDecimal(valor1);
                                        BigDecimal valorReceber_sem_antecipado = new BigDecimal(valor1 - (valor1 * (debito * 100)) / 100);

                                        BigDecimal parcelaCliente = new BigDecimal(valor1);

                                        textViewResultadosUsuario.setText("Valor a receber\n" +
                                                "Débito - Plano Veloz sem antecipação: " + colocarVirgula(String.valueOf(valorReceber_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nDébito - Plano Veloz com antecipação: " + colocarVirgula(String.valueOf(valorReceber_antecipado.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nDébito - Plano Controle sem antecipação: " + colocarVirgula(String.valueOf(valorReceber_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nDébito - Plano Controle com antecipação: " + colocarVirgula(String.valueOf(valorReceber_antecipado.setScale(0, BigDecimal.ROUND_UP))));

                                        textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente.setScale(0, BigDecimal.ROUND_UP))));

                                    } else { //REPASSAR

                                        Double valor1 = Double.parseDouble(unmask(valor).trim());
                                        BigDecimal valorCobrar_antecipado = new BigDecimal(valor1);
                                        BigDecimal valorCobrar_sem_antecipado = new BigDecimal(valor1 / (1 - debito));

                                        BigDecimal parcelaCliente_antecipado = new BigDecimal(valor1);
                                        BigDecimal parcelaCliente_sem_antecipado = new BigDecimal(valor1 / (1 - debito));

                                        textViewResultadosUsuario.setText("Valor a receber\n" +
                                                "Débito - Plano Veloz sem antecipação: " + colocarVirgula(String.valueOf(valorCobrar_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nDébito - Plano Veloz com antecipação: " + colocarVirgula(String.valueOf(valorCobrar_antecipado.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nDébito - Plano Controle sem antecipação: " + colocarVirgula(String.valueOf(valorCobrar_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))) +
                                                "\nDébito - Plano Controle com antecipação: " + colocarVirgula(String.valueOf(valorCobrar_antecipado.setScale(0, BigDecimal.ROUND_UP))));

                                        textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                colocarVirgula(String.valueOf(parcelaCliente_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                colocarVirgula(String.valueOf(parcelaCliente_antecipado.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                colocarVirgula(String.valueOf(parcelaCliente_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                colocarVirgula(String.valueOf(parcelaCliente_antecipado.setScale(0, BigDecimal.ROUND_UP))));
                                    }
                                } else if (opTaxa != "Debito") {

                                    if (opParcela.equals("Assumir")) { // ASSUMIR
                                        Double valor1 = Double.parseDouble(unmask(valor));
                                        Double opTaxa1 = Double.parseDouble(unmask(opTaxa));
                                        int opTaxaInt = Integer.parseInt(unmask(opTaxa));

                                        if (opTaxa1 > 1 && opTaxa1 <= 6) {
                                            BigDecimal parcelaCliente_veloz_antecipado = new BigDecimal(valor1 / opTaxa1);
                                            BigDecimal parcelaCliente_veloz_sem_antecipado = new BigDecimal(valor1 / opTaxa1);
                                            BigDecimal parcelaCliente_sem_antecipado = new BigDecimal(valor1 / opTaxa1);
                                            BigDecimal parcelaCliente_controle_sem_antecipado = new BigDecimal(valor1 / opTaxa1);

                                            BigDecimal valorReceber_veloz_antecipado = new BigDecimal(valor1 - (taxa_veloz_antecipado[opTaxaInt] * valor1));
                                            BigDecimal valorReceber_veloz_sem_antecipado = new BigDecimal(valor1 - (taxa_de_parcelamento_2_6 * valor1));
                                            BigDecimal valorReceber_controle_antecipado = new BigDecimal(valor1 - (taxa_controle_antecipado[opTaxaInt] * valor1));
                                            BigDecimal valorReceber_controle_sem_antecipado = new BigDecimal(valor1 - (taxa_de_parcelamento_2_6 * valor1));

                                            textViewResultadosUsuario.setText("Valor a receber\n" +
                                                    "Plano Veloz sem antecipação: " + colocarVirgula(String.valueOf(valorReceber_veloz_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nPlano Veloz com antecipação: " + colocarVirgula(String.valueOf(valorReceber_veloz_antecipado.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nPlano Controle sem antecipação: " + colocarVirgula(String.valueOf(valorReceber_controle_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nPlano Controle com antecipação: " + colocarVirgula(String.valueOf(valorReceber_controle_antecipado.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_veloz_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_veloz_antecipado.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_controle_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))));

                                        } else if (opTaxa1 > 6 && opTaxa1 <= 12) {
//                                            BigDecimal parcelaCliente_veloz_antecipado = new BigDecimal(valor1 / opTaxa1);
                                            BigDecimal parcelaCliente_veloz_sem_antecipado = new BigDecimal(valor1 / opTaxa1);
//                                            BigDecimal parcelaCliente_sem_antecipado = new BigDecimal(valor1 / opTaxa1);
                                            BigDecimal parcelaCliente_controle_sem_antecipado = new BigDecimal(valor1 / opTaxa1);

//                                            BigDecimal valorReceber_veloz_antecipado = new BigDecimal(valor1 - (taxa_veloz_antecipado[opTaxaInt] * valor1));
                                            BigDecimal valorReceber_veloz_sem_antecipado = new BigDecimal(valor1 - (taxa_de_parcelamento_7_12 * valor1));
//                                            BigDecimal valorReceber_controle_antecipado = new BigDecimal(valor1 - (taxa_veloz_antecipado[opTaxaInt] * valor1));
                                            BigDecimal valorReceber_controle_sem_antecipado = new BigDecimal(valor1 - (taxa_de_parcelamento_7_12 * valor1));

                                            textViewResultadosUsuario.setText("Valor a receber\n" +
                                                    "Plano Veloz sem antecipação: " + colocarVirgula(String.valueOf(valorReceber_veloz_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))) +
//                                                    "\nPlano Veloz com antecipação: " + colocarVirgula(String.valueOf(valorReceber_veloz_antecipado.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nPlano Controle sem antecipação: " + colocarVirgula(String.valueOf(valorReceber_controle_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))));
//                                                    "\nPlano Controle com antecipação: " + colocarVirgula(String.valueOf(valorReceber_controle_antecipado.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_veloz_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
//                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_veloz_antecipado.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_controle_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    "\nStarPay possui taxas fixas de parcelamento \nsendo de 2x - 6x = 3,90%\n" +
                                                    "7x - 12x = 4,90%. \nPor isso você receberá o mesmo valor =D");
//                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))));
                                        } else {

                                            BigDecimal parcelaCliente_credito_a_vista = new BigDecimal(valor1 / opTaxa1);
                                            BigDecimal parcelaCliente_veloz_antecipado = new BigDecimal(valor1 / opTaxa1);
                                            BigDecimal parcelaCliente_controle_antecipado = new BigDecimal(valor1 / opTaxa1);

                                            BigDecimal valorReceber_credito_a_vista = new BigDecimal(valor1 - (valor1 * (credito_a_vista)));
                                            BigDecimal valorReceber_veloz_antecipado = new BigDecimal(valor1 - (valor1 * (credito_a_vista_veloz)));
                                            BigDecimal valorReceber_controle_antecipado = new BigDecimal(valor1 - (valor1 * (credito_a_vista_controle)));

                                            textViewResultadosUsuario.setText("Valor a receber\n" +
                                                    "Plano Veloz sem antecipação: " + colocarVirgula(String.valueOf(valorReceber_credito_a_vista.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nPlano Veloz com antecipação: " + colocarVirgula(String.valueOf(valorReceber_veloz_antecipado.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nPlano Controle sem antecipação: " + colocarVirgula(String.valueOf(valorReceber_credito_a_vista.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nPlano Controle com antecipação: " + colocarVirgula(String.valueOf(valorReceber_controle_antecipado.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_credito_a_vista.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_veloz_antecipado.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_credito_a_vista.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_controle_antecipado.setScale(0, BigDecimal.ROUND_UP))));
                                        }


                                    } else { // REPASSAR // TA OK
                                        Double valor1 = Double.parseDouble(unmask(valor));
                                        Double opTaxa1 = Double.parseDouble(unmask(opTaxa));
                                        int opTaxaInt = Integer.parseInt(unmask(opTaxa));

                                        if (opTaxa1 > 1 && opTaxa1 <= 6) {
                                            BigDecimal valorCobrar_veloz_antecipado = new BigDecimal(valor1+((valor1*taxa_de_parcelamento_2_6)+(valor1*taxa_veloz_antecipado[opTaxaInt])));
                                            BigDecimal valorCobrar_veloz_sem_antecipado = new BigDecimal(valor1 / (1 - (taxa_de_parcelamento_2_6)));
                                            BigDecimal valorCobrar_controle_antecipado = new BigDecimal(valor1+((valor1*taxa_de_parcelamento_2_6)+(valor1*taxa_controle_antecipado[opTaxaInt])));
                                            BigDecimal valorCobrar_controle_sem_antecipado = new BigDecimal(valor1 / (1 - (taxa_de_parcelamento_2_6)));

                                            BigDecimal parcelaCliente_veloz_antecipado = new BigDecimal((valor1+((valor1*taxa_de_parcelamento_2_6)+(valor1*taxa_veloz_antecipado[opTaxaInt])))/opTaxa1);
                                            BigDecimal parcelaCliente_veloz_sem_antecipado = new BigDecimal((valor1 / (1 - (taxa_de_parcelamento_2_6)))/opTaxa1);
                                            BigDecimal parcelaCliente_controle_antecipado = new BigDecimal((valor1+((valor1*taxa_de_parcelamento_2_6)+(valor1*taxa_controle_antecipado[opTaxaInt])))/opTaxa1);
                                            BigDecimal parcelaCliente_controle_sem_antecipado = new BigDecimal((valor1 / (1 - (taxa_de_parcelamento_2_6)))/opTaxa1);

                                            textViewResultadosUsuario.setText("Valor a Cobrar\n" +
                                                    "Plano Veloz sem antecipação: " + colocarVirgula(String.valueOf(valorCobrar_veloz_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nPlano Veloz com antecipação: " + colocarVirgula(String.valueOf(valorCobrar_veloz_antecipado.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nPlano Controle sem antecipação: " + colocarVirgula(String.valueOf(valorCobrar_controle_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nPlano Controle com antecipação: " + colocarVirgula(String.valueOf(valorCobrar_controle_antecipado.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_veloz_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_veloz_antecipado.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_controle_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_controle_antecipado.setScale(0, BigDecimal.ROUND_UP))));

                                        } else if(opTaxa1 > 6 && opTaxa1 <= 12){
//                                            BigDecimal valorCobrar_veloz_antecipado = new BigDecimal(valor1+((100*taxa_de_parcelamento_7_12)+(100*taxa_veloz_antecipado[opTaxaInt])));
                                            BigDecimal valorCobrar_veloz_sem_antecipado = new BigDecimal(valor1 / (1 - (taxa_de_parcelamento_7_12)));
//                                            BigDecimal valorCobrar_controle_antecipado = new BigDecimal(valor1+((100*taxa_de_parcelamento_7_12)+(100*taxa_controle_antecipado[opTaxaInt])));
                                            BigDecimal valorCobrar_controle_sem_antecipado = new BigDecimal(valor1 / (1 - (taxa_de_parcelamento_7_12)));

//                                            BigDecimal parcelaCliente_veloz_antecipado = new BigDecimal(valor1+((100*taxa_de_parcelamento_7_12)+(100*taxa_veloz_antecipado[opTaxaInt])));
                                            BigDecimal parcelaCliente_veloz_sem_antecipado = new BigDecimal(valor1 / (1 - (taxa_de_parcelamento_7_12)));
//                                            BigDecimal parcelaCliente_controle_antecipado = new BigDecimal(valor1+((100*taxa_de_parcelamento_7_12)+(100*taxa_controle_antecipado[opTaxaInt])));
                                            BigDecimal parcelaCliente_controle_sem_antecipado = new BigDecimal(valor1 / (1 - (taxa_de_parcelamento_7_12)));

                                            textViewResultadosUsuario.setText("Valor a Cobrar\n" +
                                                    "Plano Veloz sem antecipação: " + colocarVirgula(String.valueOf(valorCobrar_veloz_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))) +
//                                                    "\nPlano Veloz com antecipação: " + colocarVirgula(String.valueOf(valorCobrar_veloz_antecipado.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nPlano Controle sem antecipação: " + colocarVirgula(String.valueOf(valorCobrar_controle_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))));
//                                                    "\nPlano Controle com antecipação: " + colocarVirgula(String.valueOf(valorCobrar_controle_antecipado.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_veloz_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
//                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_veloz_antecipado.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_controle_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    "StarPay possui taxas fixas de parcelamento \nsendo de 2x - 6x = 3,90%\n" +
                                                    "7x - 12x = 4,90%. \nPor isso você receberá o mesmo valor =D");
//                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_controle_antecipado.setScale(0, BigDecimal.ROUND_UP))));
                                        }else{
                                            BigDecimal valorCobrar_veloz_antecipado = new BigDecimal(valor1 / (1 - (taxa_veloz_antecipado[opTaxaInt])));
                                            BigDecimal valorCobrar_veloz_sem_antecipado = new BigDecimal(valor1 / (1 - (credito_a_vista)));
                                            BigDecimal valorCobrar_controle_antecipado = new BigDecimal(valor1 / (1 - (taxa_controle_antecipado[opTaxaInt])));
                                            BigDecimal valorCobrar_controle_sem_antecipado = new BigDecimal(valor1 / (1 - (credito_a_vista)));

                                            BigDecimal parcelaCliente_veloz_antecipado = new BigDecimal((valor1 / (1 - (taxa_veloz_antecipado[opTaxaInt])))/opTaxa1);
                                            BigDecimal parcelaCliente_veloz_sem_antecipado = new BigDecimal((valor1 / (1 - (credito_a_vista)))/opTaxa1);
                                            BigDecimal parcelaCliente_controle_antecipado = new BigDecimal((valor1 / (1 - (taxa_controle_antecipado[opTaxaInt])))/opTaxa1);
                                            BigDecimal parcelaCliente_controle_sem_antecipado = new BigDecimal((valor1 / (1 - (credito_a_vista)))/opTaxa1);

                                            textViewResultadosUsuario.setText("Valor a Cobrar\n" +
                                                    "Plano Veloz sem antecipação: " + colocarVirgula(String.valueOf(valorCobrar_veloz_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nPlano Veloz com antecipação: " + colocarVirgula(String.valueOf(valorCobrar_veloz_antecipado.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nPlano Controle sem antecipação: " + colocarVirgula(String.valueOf(valorCobrar_controle_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))) +
                                                    "\nPlano Controle com antecipação: " + colocarVirgula(String.valueOf(valorCobrar_controle_antecipado.setScale(0, BigDecimal.ROUND_UP))));

                                            textViewResultadosCliente.setText("Parcela do Cliente\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_veloz_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_veloz_antecipado.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_controle_sem_antecipado.setScale(0, BigDecimal.ROUND_UP))) + "\n" +
                                                    opTaxa + "\t" + colocarVirgula(String.valueOf(parcelaCliente_controle_antecipado.setScale(0, BigDecimal.ROUND_UP))));
                                        }
                                    }
                                }

                            } else {
                                Log.d("TAGG", "Documento não existe");
                            }

                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
    }

    public static String unmask(String s) {
        return s.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[)]", "").replaceAll("[R$]", "")
                .replaceAll("[,]", ".").replaceAll("[x]", "");
    }

    public String colocarVirgula(String valor) {
        int tamanho = valor.length();
        String virgula = ",";
        String ponto = ".";
        if (tamanho == 1) {
            String valor1 = valor.substring(tamanho - tamanho, tamanho);
            valor = "0" + virgula + "0" + valor1;
        } else if (tamanho == 2) {
            String valor1 = valor.substring(tamanho - tamanho, tamanho);
            valor = "0" + virgula + valor1;
        } else if (tamanho == 3) {
            String valor1 = valor.substring(tamanho - tamanho, tamanho - 2);
            String valor2 = valor.substring(tamanho - 2, tamanho);
            valor = valor1 + virgula + valor2;
        } else if (tamanho > 3 && tamanho <= 7) {
            String valor1 = valor.substring(tamanho - tamanho, tamanho - 2);
            String valor2 = valor.substring(tamanho - 2, tamanho);
            valor = valor1 + virgula + valor2;
        } else if (tamanho >= 8) {
            String valor1 = valor.substring(tamanho - tamanho, (tamanho + 3) - tamanho);
            String valor2 = valor.substring((tamanho + 3) - tamanho, tamanho - 2);
            String valor3 = valor.substring(tamanho - 2, tamanho);
            valor = valor1 + ponto + valor2 + virgula + valor3;
        }

        return valor;
    }

}
