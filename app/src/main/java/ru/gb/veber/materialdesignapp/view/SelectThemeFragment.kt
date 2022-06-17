import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.radiobutton.MaterialRadioButton
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.BottomNavigationLayoutBinding
import ru.gb.veber.materialdesignapp.databinding.SelectThemeLayoutBinding
import ru.gb.veber.materialdesignapp.utils.FILE_SETTINGS
import ru.gb.veber.materialdesignapp.utils.KEY_MODE_DARK
import ru.gb.veber.materialdesignapp.utils.KEY_THEME


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
    ): View? {
        _binding = SelectThemeLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        (getNumTheme()?.let { binding.radioButtons.getChildAt(it) } as MaterialRadioButton).isChecked =
            true

        binding.rButtonTeal.setOnClickListener {
            putTheme(0)
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            putModeTheme(false)
        }
        binding.rButtonBlue.setOnClickListener {
            putTheme(1)
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            putModeTheme(false)
        }
        binding.rButtonGreen.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            putTheme(2)
            putModeTheme(false)
        }
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

    private fun getNumTheme(): Int? {
        return activity?.let {
            it.getSharedPreferences(FILE_SETTINGS, Context.MODE_PRIVATE).getInt(KEY_THEME, 0)
        }
    }
}
