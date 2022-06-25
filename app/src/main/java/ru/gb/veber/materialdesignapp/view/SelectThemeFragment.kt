import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.radiobutton.MaterialRadioButton
import ru.gb.veber.materialdesignapp.databinding.SelectThemeLayoutBinding
import ru.gb.veber.materialdesignapp.utils.*


class SelectThemeFragment : BottomSheetDialogFragment() {

    private var _binding: SelectThemeLayoutBinding? = null
    private val binding: SelectThemeLayoutBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SelectThemeLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        init()
    }

    private fun init() {
        with(binding)
        {
            (radioButtons.getChildAt(getNumTheme()) as MaterialRadioButton).isChecked = true
            rButtonTeal.setOnClickListener { clickRadioButton(KEY_THEME_TEAL) }
            rButtonBlue.setOnClickListener { clickRadioButton(KEY_THEME_BLUE) }
            rButtonGreen.setOnClickListener { clickRadioButton(KEY_THEME_GREEN) }
        }
    }

    private fun clickRadioButton(key: Int) {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        putTheme(key)
        putModeTheme(false)
        dismiss()
    }

    private fun putModeTheme(key: Boolean) {
        activity?.let {
            it.getSharedPreferences(FILE_SETTINGS, Context.MODE_PRIVATE).edit()
                .putBoolean(KEY_MODE_DARK, key).apply()
        }
    }

    private fun putTheme(key: Int) {
        activity?.let {
            it.getSharedPreferences(FILE_SETTINGS, Context.MODE_PRIVATE).edit()
                .putInt(KEY_THEME, key).apply()
            it.recreate()
        }
    }

    private fun getNumTheme(): Int {
        return activity?.let {
            it.getSharedPreferences(FILE_SETTINGS, Context.MODE_PRIVATE).getInt(KEY_THEME, 0)
        } ?: 0
    }
}
