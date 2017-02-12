package com.vincent.sourvide;

import java.util.List;

import com.vincent.sourvide.R;
import com.vincent.sourvide.base.Constant;
import com.vincent.sourvide.utils.ExitUtil;
import com.vincent.sourvide.utils.SharedPreferencesUtil;
import com.vincent.sourvide.utils.ToastUtils;
import com.vincent.sourvide.view.Anticlockwise;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private TextView temperatureText;
	private TextView timeText;
	private Chronometer runTime;
	private TextView tempText;
	private TextView timerText;
	private ImageView switchImg;
	private TextView temperatureCenterText;
	private Anticlockwise setTime;
	private ImageView reduceImage;
	private ImageView addImage;
	private SeekBar setbarSelected;

	private static String TAG = MainActivity.class.getSimpleName();
	private SharedPreferencesUtil shared;
	private double currentValues;
	private int time = -2;
	private boolean isStart = false; // true���ڼ�ʱ false û�м�ʱ
	// private boolean start= false;//��ʾ�ر�

	private Vibrator vibrator = null;

	private boolean isExit = false;
	
	private String[] numberList = { "40","41","42","43","44","45","46","47","48","49","50",
			"51","52","53","54","55","56","57","58","59","60",
			"61","62","63","64","65","66","67","68","69","60",};
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		currentValues = shared.getInt("currentValues");
		if (currentValues == 0) {
			currentValues = 40.0;
		}
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		// cookingTimeText.setText("00:00");
		runTime.start();
		setbarSelected.setMax(90);
		setbarSelected.setProgress((int) currentValues);
		temperatureCenterText.setText(String.valueOf(currentValues));
//		temperatureCenterText.setSelection(temperatureCenterText.getText().toString().length());// �ѹ��ŵ����
		setbarSelected.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				temperatureCenterText.setText(String.valueOf(currentValues));
//				temperatureCenterText.setSelection(temperatureCenterText.getText().toString().length());
				currentValues = progress;
				ToastUtils.defaultToast(String.valueOf(currentValues));
				shared.putInt("currentValues", (int) currentValues);

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
		setTime.setOnTimeCompleteListener(new Anticlockwise.OnTimeCompleteListener() {
			@Override
			public void onTimeComplete() {
				if (time == -2) {
					return;
				}
				setTime.initTime(-2);
				ToastUtils.defaultToast("����ʱ����");
				switchImg.setImageResource(R.drawable.icon_start);
				isStart = false;
				time = -2;
				startShake();
			}
		});
	}

	/**
	 * @Title: init @Description: TODO(������һ�仰�����������������) @param ���� @return void
	 *         �������� @throws
	 */
	private void init() {
		temperatureText = (TextView) findViewById(R.id.temperature_text);
		timeText = (TextView) findViewById(R.id.time_text);
		runTime = (Chronometer) findViewById(R.id.chronometer_run_time);
		tempText = (TextView) findViewById(R.id.temp_text);
		timerText = (TextView) findViewById(R.id.timer_text);
		switchImg = (ImageView) findViewById(R.id.switch_img);
		temperatureCenterText = (TextView) findViewById(R.id.temperature_center_text);
		setTime = (Anticlockwise) findViewById(R.id.set_time);
		reduceImage = (ImageView) findViewById(R.id.reduce_imgae);
		addImage = (ImageView) findViewById(R.id.add_imgae);
		setbarSelected = (SeekBar) findViewById(R.id.setbar_selected);
		shared = new SharedPreferencesUtil(this, Constant.SHAREDPREFERENCES_NAME);

		setTime.setOnClickListener(this);
		timerText.setOnClickListener(this);
		tempText.setOnClickListener(this);
		switchImg.setOnClickListener(this);
		reduceImage.setOnClickListener(this);
		addImage.setOnClickListener(this);
		temperatureCenterText.setOnClickListener(this);
		
		
		temperatureCenterText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// ����ǰ
//				temperatureCenterText.setSelection(temperatureCenterText.getText().toString().length());//�ѹ��������
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// ������
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				//�����
				try{
					String p = temperatureCenterText.getText().toString();
					double d = Double.valueOf(p);
					if(40<d||d>90){
						d=50.0;
						ToastUtils.defaultToast("�¶�ֵֻ����40-90�淶Χ");
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}

	private void startShake() {
		try{
			if (vibrator != null) {
				long[] pattern = { 1000, 2000, 1000, 2000 }; // ֹͣ ���� ֹͣ ����
				vibrator.vibrate(pattern, 2); // �ظ����������pattern ���ֻ����һ�Σ�index��Ϊ-1
				new AlertDialog.Builder(this).setMessage("�ر�����")
						.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
								stopShake();
							}
						}).setCancelable(false).create().show();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void stopShake() {
		super.onStop();
		if (vibrator != null) {
			vibrator.cancel();
		}
	}

	@Override
	public void onBackPressed() {
		ExitUtil.isQuit(this, "�ٵ�һ���˳�");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.temp_text:
			ToastUtils.defaultToast("Temp");
			break;
		case R.id.timer_text:
			ToastUtils.defaultToast("Timer");
			break;
		case R.id.set_time:
			final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_settime, null);
			/*
			 * Dialog dialog = new Dialog(MainActivity.this);
			 * dialog.setTitle("����ʱ��"); dialog.setContentView(view);
			 * dialog.show();
			 */
			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			builder.setTitle("����ʱ��").setView(view).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			}).setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					try {
						EditText etTime = (EditText) view.findViewById(R.id.et_input_time);
						String inputTime = etTime.getText().toString().trim();
						int l = Integer.valueOf(inputTime);
						time = l;
						setTime.initTime(l);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).setCancelable(false).show();
			// .create().show();
			break;
		case R.id.switch_img:
			if (!isStart) {
				if (time == -2) {
					ToastUtils.defaultToast("������ʱ��");
					return;
				}
				if(currentValues<40||currentValues>90){
					ToastUtils.defaultToast("�¶�ֵֻ����40-90��֮��,�����������¶�ֵ");
					return;
				}
				ToastUtils.defaultToast("��ʼ����ʱ");
				switchImg.setImageResource(R.drawable.icon_stop);
				isStart = true;
				setTime.start();
			} else {
				isStart = false;
				setTime.onPause();
				// cookingTimeText.setBase(SystemClock.elapsedRealtime());
				ToastUtils.defaultToast("��ͣ��ʱ");
				switchImg.setImageResource(R.drawable.icon_start);
				time = -1;
			}
			break;
		case R.id.reduce_imgae:
			if (setbarSelected.getProgress() > 90) {
				setbarSelected.setProgress(90);
				temperatureCenterText.setText("90.0");
			} else {
				setbarSelected.setProgress((int) currentValues--);
				temperatureCenterText.setText(String.valueOf(currentValues));
				ToastUtils.defaultToast(String.valueOf(currentValues));
				shared.putInt("currentValues", (int) currentValues);
			}
//			temperatureCenterText.setSelection(temperatureCenterText.getText().toString().length());
			break;
		case R.id.add_imgae:
			setbarSelected.setProgress((int) currentValues++);
			temperatureCenterText.setText(String.valueOf(currentValues));
//			temperatureCenterText.setSelection(temperatureCenterText.getText().toString().length());
			ToastUtils.defaultToast(String.valueOf(currentValues));
			shared.putInt("currentValues", (int) currentValues);
			break;
		case R.id.temperature_center_text:
			final View views = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_set_wendu, null);
			/*
			 * Dialog dialog = new Dialog(MainActivity.this);
			 * dialog.setTitle("����ʱ��"); dialog.setContentView(view);
			 * dialog.show();
			 */
			AlertDialog.Builder builders = new AlertDialog.Builder(MainActivity.this);
			builders.setTitle("�����¶�").setView(views).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			}).setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			}).setCancelable(false).show();
			break;
		}

	}
}
