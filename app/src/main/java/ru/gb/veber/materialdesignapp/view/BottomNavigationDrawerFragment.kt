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
import ru.gb.veber.materialdesignapp.utils.FILE_SETTINGS
import ru.gb.veber.materialdesignapp.utils.KEY_THEME


class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding: BottomNavigationLayoutBinding
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
        _binding = BottomNavigationLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {

                R.id.navigation_one -> Toast.makeText(context, "1", Toast.LENGTH_SHORT).show()
                R.id.navigation_two -> Toast.makeText(context, "2", Toast.LENGTH_SHORT).show()
                R.id.switch_item -> {
                    //Приходится нажимать чтоб сработало
                    (menuItem.actionView as Switch).setOnCheckedChangeListener { button, isCheked ->
                        if (isCheked) {
                            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                        } else {
                            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                        }
                    }
                }
            }
            true
        }
        (getNumTheme()?.let { binding.radioButtons.getChildAt(it) } as MaterialRadioButton).isChecked =
            true

        binding.tealButton.setOnClickListener {
            putTheme(0)
        }
        binding.blueButton.setOnClickListener {
            putTheme(1)
        }
        binding.greenButton.setOnClickListener {
            putTheme(2)
            //AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_bottom_navigation, menu)
        super.onCreateOptionsMenu(menu, inflater)
        //Через меню не работает
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.switch_item -> dismiss()
        }
        return super.onOptionsItemSelected(item)
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
