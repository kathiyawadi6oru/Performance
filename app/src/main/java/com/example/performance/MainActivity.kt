package com.example.performance

import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    lateinit var ed1: EditText
    lateinit var ed2:EditText
    lateinit var b1: Button
    lateinit var b2:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ed1=findViewById(R.id.editText);
        ed2=findViewById(R.id.editText2);
        b1=findViewById(R.id.button);
        b2=findViewById(R.id.b2)

        b1.setOnClickListener {
            val ifilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            val batteryStatus = registerReceiver(null, ifilter)

            val status = batteryStatus!!.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING &&
                    status == BatteryManager.BATTERY_STATUS_FULL

            val chargePlug = batteryStatus!!.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
            val usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB
            val acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC

            if(isCharging) {
                Toast.makeText(applicationContext, "Mobile is charging is full",
                        Toast.LENGTH_LONG).show()
            } else if(acCharge) {
                Toast.makeText(applicationContext, "Mobile is charging on AC",
                        Toast.LENGTH_LONG).show()
            }else if (usbCharge) {
                Toast.makeText(applicationContext, "Mobile is charging on USB",
                        Toast.LENGTH_LONG).show()
            }
        }
        b2.setOnClickListener{

            // Call battery manager service
            val bm = applicationContext.getSystemService(BATTERY_SERVICE) as BatteryManager

            // Get the battery percentage and store it in a INT variable
            val batLevel:Int = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

            // Display the variable using a Toast
            Toast.makeText(applicationContext,"Battery is $batLevel%",Toast.LENGTH_LONG).show()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
    }
}