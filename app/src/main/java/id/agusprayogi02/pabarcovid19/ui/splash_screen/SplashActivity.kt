package id.agusprayogi02.pabarcovid19.ui.splash_screen

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.animation.DecelerateInterpolator
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.google.firebase.auth.FirebaseAuth
import id.agusprayogi02.pabarcovid19.MainActivity
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.session.SessionData
import id.agusprayogi02.pabarcovid19.ui.auth.LoginActivity
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    lateinit var springForce: SpringForce
    private val auth = FirebaseAuth.getInstance()

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            springForce = SpringForce(0f)
            splash_layout.pivotX = 0f
            splash_layout.pivotY = 0f

            val springAnimation = SpringAnimation(splash_layout, DynamicAnimation.ROTATION).apply {
                springForce.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
                springForce.stiffness = SpringForce.STIFFNESS_VERY_LOW
            }

            springAnimation.spring = springForce
            springAnimation.setStartValue(80f)
            springAnimation.addEndListener { _, _, _, _ ->
                val displayMetrics = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(displayMetrics)
                val height = displayMetrics.heightPixels.toFloat()
                val width = displayMetrics.widthPixels
                splash_layout.animate()
                    .setStartDelay(1)
                    .translationXBy(width.toFloat() / 2)
                    .translationYBy(height)
                    .setListener(object : Animator.AnimatorListener {
                        override fun onAnimationRepeat(p0: Animator?) {

                        }

                        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                        override fun onAnimationEnd(p0: Animator?) {
                            val intent = if (auth.currentUser != null) {
                                SessionData.session(applicationContext)
                                SessionData["UserData"] = auth.currentUser!!.uid
                                Intent(applicationContext, MainActivity::class.java)
                            } else {
                                Intent(applicationContext, LoginActivity::class.java)
                            }
                            finish()
                            startActivity(intent)

                            overridePendingTransition(0, 0)
                        }

                        override fun onAnimationCancel(p0: Animator?) {

                        }

                        override fun onAnimationStart(p0: Animator?) {

                        }

                    })
                    .setInterpolator(DecelerateInterpolator(1f))
                    .start()
            }
            springAnimation.start()
        }, 4000)
    }
}
