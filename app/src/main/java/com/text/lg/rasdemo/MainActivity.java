package com.text.lg.rasdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    private KeyPair keyPair;
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;
    private String text;
    private String encryStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        textRSA();
    }

    public void textRSA() {
        text = "测试加密 RSA";

        keyPair = RSAUtils.generateRSAKeyPair(RSAUtils.DEFAULT_KEY_SIZE);
        // 公钥
        publicKey = (RSAPublicKey) keyPair.getPublic();
        // 私钥
        privateKey = (RSAPrivateKey) keyPair.getPrivate();

    }


    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button1:
                //公钥加密
                byte[] encryptBytes = new byte[0];
                try {
                    encryptBytes = RSAUtils.encryptByPublicKeyForSpilt(text.getBytes(), publicKey.getEncoded());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                encryStr = Base64.encode(encryptBytes);
                Log.d("RSA", "加密后的数据：" + encryStr);
                tvName.setText(encryStr);
                break;
            case R.id.button2:
                //私钥解密
                byte[] decryptBytes = new byte[0];
                try {
                    decryptBytes = RSAUtils.decryptByPrivateKeyForSpilt(Base64.decode(encryStr), privateKey.getEncoded());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String decryStr = new String(decryptBytes);
                Log.d("RSA", "解密后的数据：" + decryStr);
                tvName.setText(decryStr);
                break;
            case R.id.button3:
                //私钥加密
                byte[] encryptBytes2 = new byte[0];
                try {
                    encryptBytes2 = RSAUtils.encryptByPrivateKeyForSpilt(text.getBytes(), privateKey.getEncoded());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                encryStr = Base64.encode(encryptBytes2);
                Log.d("RSA", "加密后的数据：" + encryStr);
                tvName.setText(encryStr);
                break;
            case R.id.button4:

                //公钥解密
                byte[] decryptBytes2 = new byte[0];
                try {
                    decryptBytes2 = RSAUtils.decryptByPublicKeyForSpilt(Base64.decode(encryStr), publicKey.getEncoded());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                decryStr = new String(decryptBytes2);
                Log.d("RSA", "解密后的数据：" + decryStr);
                tvName.setText(decryStr);
                break;

                default:
        }
    }
}
