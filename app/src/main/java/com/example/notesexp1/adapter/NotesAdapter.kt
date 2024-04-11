package com.example.notesexp1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesexp1.R
import com.example.notesexp1.databinding.ListItemBinding
import com.example.notesexp1.models.Note
import kotlinx.coroutines.flow.asFlow
import kotlin.random.Random

class NotesAdapter(var context: Context,val listener:NotesItemClickListener):
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {


    inner class NoteViewHolder(var binding:ListItemBinding):RecyclerView.ViewHolder(binding.root) {

    }

    private val noteList = ArrayList<Note>()
        private val fullList = ArrayList<Note>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = noteList[position]
        with(holder){
            binding.noteTitle.text = currentNote.title
            binding.noteTitle.isSelected= true

            binding.noteNote.text = currentNote.note
            binding.noteDate.text = currentNote.date
            binding.noteDate.isSelected = true

            binding.cardLayout.setCardBackgroundColor(binding.cardLayout.resources.getColor(randomColor(),null))

            binding.cardLayout.setOnClickListener {
                listener.onItemClicked(noteList[adapterPosition])
            }

            binding.cardLayout.setOnLongClickListener {
                listener.onLongItemClicked(noteList[adapterPosition],binding.cardLayout)
                true
            }
        }
    }

    fun updateList(newList:List<Note>){
        fullList.clear()
        fullList.addAll(newList)

        noteList.clear()
        noteList.addAll(newList)
        notifyDataSetChanged()
    }

    fun filterList(search:String){
        noteList.clear()
        for (item in fullList){
            if (item.title?.lowercase()?.contains(search.lowercase()) == true ||
                item.note?.lowercase()?.contains(search.lowercase()) == true) {

                noteList.add(item)
            }
        }
        notifyDataSetChanged()
    }

    fun randomColor():Int{
        val list = ArrayList<Int>()
        list.add(R.color.NoteColor1)
        list.add(R.color.NoteColor2)
        list.add(R.color.NoteColor3)
        list.add(R.color.NoteColor4)
        list.add(R.color.NoteColor5)
        list.add(R.color.NoteColor)

        val seed = Random.nextInt(5)+1
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]
    }

    interface NotesItemClickListener{
        fun onItemClicked(note: Note)
        fun onLongItemClicked(note: Note,cardView: CardView)
    }

}