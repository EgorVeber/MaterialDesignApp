import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.widget.SearchView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.radiobutton.MaterialRadioButton
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.BottomNavigationLayoutBinding
import ru.gb.veber.materialdesignapp.utils.FILE_SETTINGS
import ru.gb.veber.materialdesignapp.utils.KEY_MODE_DARK
import ru.gb.veber.materialdesignapp.utils.KEY_THEME


class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding: BottomNavigationLayoutBinding
        get() = _binding!!

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private var menuSwitch: Switch? = null

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomNavigationLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        init()
    }

    private fun init() {
        setNavigationItemSelected()
        menuSwitch =
            (binding.navigationView.menu.findItem(R.id.switch_item).actionView as Switch).apply {
                setOnCheckedChangeListener { button, isCheked ->
                    if (isCheked) {
                        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                        putModeTheme(true)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                        putModeTheme(false)
                    }
                }
                isChecked = getModeTheme()
            }
    }

    private fun putModeTheme(key: Boolean) {
        activity?.let {
            it.getSharedPreferences(FILE_SETTINGS, Context.MODE_PRIVATE).edit()
                .putBoolean(KEY_MODE_DARK, key).apply()
        }
    }

    private fun getModeTheme(): Boolean {
        activity?.let {
            return it.getSharedPreferences(FILE_SETTINGS, Context.MODE_PRIVATE)
                .getBoolean(KEY_MODE_DARK, false)
        }
        return false
    }


    private fun setNavigationItemSelected() {
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_one -> Toast.makeText(context, "1", Toast.LENGTH_SHORT).show()
                R.id.navigation_two -> Toast.makeText(context, "2", Toast.LENGTH_SHORT).show()
                R.id.switch_item -> {
                    menuSwitch?.isChecked = menuSwitch?.isChecked != true
                }
            }
            dismiss()
            true
        }
    }
}
