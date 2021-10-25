package com.example.myteachingreborn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Aboard extends AppCompatActivity implements View.OnClickListener {
    Button newGame1, exit, info, createButt, menu;
    int [][] BoardPrice = new int[100][100];
    FrameLayout layout;
    Button[][] b = new Button[30][30];
    androidx.gridlayout.widget.GridLayout gridLayout;
    final int lenW = 9;
    final int lenH = 13;
    int fx = 1;
    int x;
    int y;
    int knopkaStart = 0;
    int jj = 0;
    int ii = 0;
    int tag = 0;
    int pobeda = 0;
    int regimIgri;
    int draw = lenH*lenW;
    int w = 1000, h = 1600;
    Drawable pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        regimIgri = arguments.getInt("reg");
        setContentView(R.layout.activity_gameboard);

        gridLayout = (androidx.gridlayout.widget.GridLayout) findViewById(R.id.grid);
        layout = findViewById(R.id.lay);
        newGame1 = (Button) findViewById(R.id.menu);
        menu = (Button) findViewById(R.id.newGame);
        info = (Button) findViewById(R.id.inform);
        exit = (Button) findViewById(R.id.exit);
        createButt = (Button) findViewById(R.id.createbut);

        info.setOnClickListener(this);
        exit.setOnClickListener(this);
        newGame1.setOnClickListener(this);
        createButt.setOnClickListener(this);
        menu.setOnClickListener(this);

        gridLayout.setRowCount(lenH);
        gridLayout.setColumnCount(lenW);

    }
    @Override
    public void onClick(View view)
    {
        String pp;
        if(view.getId() == R.id.exit)
        {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i){
                        case DialogInterface.BUTTON_POSITIVE:
                            Toast tos = Toast.makeText(getApplicationContext(),
                                    "Good bye my friend", Toast.LENGTH_SHORT);
                            tos.show();
                            finishAffinity();
                            System.exit(0);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder.setTitle("Exit").setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
        else if (view.getId() == R.id.newGame && knopkaStart == 1)
        {
            newGame();
        }
        else if (view.getId() == R.id.inform)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Information")
                    .setMessage("In this game, to win, you will need to be the " +
                            "first to fill five in a row with the desired " +
                            "symbol (X or 0), and you also need to take care that " +
                            "the opponent does not do this earlier.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            builder.show();
        }
        else if(view.getId() == R.id.createbut)
        {
            knopkaStart = 1;
            layout.removeView(createButt);
            for (int i = 0; i < lenH; i++) {
                for (int j = 0; j < lenW; j++) {
                    b[j][i] = new Button(this);
                    b[j][i].setLayoutParams(new LinearLayout.LayoutParams(gridLayout.getWidth()/lenW, gridLayout.getHeight()/lenH));
                    b[j][i].setTag(tag);
                    pi = b[j][i].getBackground();
                    tag++;
                    b[j][i].setOnClickListener(this);
                    gridLayout.addView(b[j][i]);
                }
            }
        }
        else if (view.getId() == R.id.menu)
        {
            finish();
        }
        else
        {
            pp = view.getTag().toString();
            x = Integer.parseInt(pp) % lenW;
            y = Integer.parseInt(pp) / lenW;
            if(regimIgri == 0)
            {
                if (b[x][y].getText().equals("") && pobeda == 0)
                    if(fx == 1)
                    {
                        draw--;
                        fx = 0;
                        b[x][y].setText("X");
                        CheckWin(x,y,'X');
                        if(draw == 0 && pobeda == 0)
                        {
                            win('d');
                        }
                    }
                    else
                    {
                        draw--;
                        fx = 1;
                        b[x][y].setText("0");
                        CheckWin(x,y,'0');
                        if(draw == 0 && pobeda == 0)
                        {
                            win('d');
                        }
                    }
            }
            else if (regimIgri == 1) //norm bot
            {
                if (b[x][y].getText().equals("") && pobeda == 0)
                {
                    Player(Integer.parseInt(pp));     //изменение ценников, цвета и текста кнопки
                    if (pobeda != 1)
                        Bot();   //ход бота
                }
            }
            else if (regimIgri == 2) //hard bot
            {
                if (b[x][y].getText().equals("") && pobeda == 0)
                {
                    Player(Integer.parseInt(pp));     //изменение ценников, цвета и текста кнопки
                    if (pobeda != 1)
                        Bot();   //ход бота
                }
            }
        }
    }
    void CheckWin(int j, int i, char x) //проверка победы
    {
        int cs = 0;
        int cs1 = 0;
        int course = 1;
        //Проверка вертикаль
        for (int k = 1; k < 5; ++k)
        {
            if (x == 48)
            {
                if ((j + k < lenW) && (b[j + k][i].getText().equals("0")))
                {
                    course++;

                }
			else
                {
                    break;
                }
                if (course == 5)
                {
                    for (int kk = 0; kk < 5; kk++)
                    {
                        b[j + kk][i].setBackgroundColor(Color.BLUE);
                        b[j + kk][i].setTextColor(Color.BLACK);
                    }
                    win(x);
                    return;
                }

            }
            else if (x == 'X')
            {
                if ((j + k < lenW) && (b[j + k][i].getText().equals("X")))
                {
                    course++;

                }
			else
                {
                    break;
                }
                if (course == 5)
                {
                    for (int kk = 0; kk < 5; kk++)
                    {
                        b[j + kk][i].setBackgroundColor(Color.RED);
                        b[j + kk][i].setTextColor(Color.BLACK);
                    }

                    win(x);
                    return;
                }

            }
        }
        cs = course;
        for (int k = 1; k < 5; ++k)
        {
            if (x == 48)
            {
                if ((j - k >= 0) && (b[j - k][i].getText().equals("0")))
                {
                    course++;
                    cs1++;
                }
			else
                {
                    break;
                }
                if (course == 5)
                {
                    if (cs + cs1 == 5)
                    {
                        for (int kk = 0; kk <= cs1; kk++)
                        {
                            b[j - kk][i].setBackgroundColor(Color.BLUE);
                            b[j - kk][i].setTextColor(Color.BLACK);
                        }
                        for (int kk = 0; kk < cs; kk++)
                        {
                            b[j + kk][i].setBackgroundColor(Color.BLUE);
                            b[j + kk][i].setTextColor(Color.BLACK);
                        }
                    }
                    else
                    {
                        for (int kk = 0; kk < 5; kk++)
                        {
                            b[j - kk][i].setBackgroundColor(Color.BLUE);
                            b[j - kk][i].setTextColor(Color.BLACK);
                        }
                    }


                    win(x);
                    return;
                }
            }
            else if (x == 'X')
            {
                if ((j - k >= 0) && (b[j - k][i].getText().equals("X")))
                {
                    course++;
                    cs1++;
                }
			else
                {
                    break;
                }
                if (course == 5)
                {
                    if (cs + cs1 == 5)
                    {
                        for (int kk = 0; kk <= cs1; kk++)
                        {
                            b[j - kk][i].setBackgroundColor(Color.RED);
                            b[j - kk][i].setTextColor(Color.BLACK);

                        }
                        for (int kk = 0; kk < cs; kk++)
                        {
                            b[j + kk][i].setBackgroundColor(Color.RED);
                            b[j + kk][i].setTextColor(Color.BLACK);

                        }
                    }
                    else
                    {
                        for (int kk = 0; kk < 5; kk++)
                        {
                            b[j - kk][i].setBackgroundColor(Color.RED);
                            b[j - kk][i].setTextColor(Color.BLACK);

                        }
                    }
                    win(x);
                    return;
                }
            }

        }
        course = 1;
        cs1 = 0;
        //проверка горизонт
        for (int k = 1; k < 5; ++k)
        {
            if (x == 48)
            {
                if ((i + k < lenH) && (b[j][i + k].getText().equals("0")))
                {
                    course++;
                }
			else
                {
                    break;
                }
                if (course == 5)
                {
                    for (int kk = 0; kk < 5; kk++)
                    {
                        b[j][i+kk].setBackgroundColor(Color.BLUE);
                        b[j][i+kk].setTextColor(Color.BLACK);
                    }
                    win(x);
                    return;
                }
                cs = course;
            }
            else
            {
                if ((i + k < lenH) && (b[j][i + k].getText().equals("X")))
                {
                    course++;
                }
			else
                {
                    break;
                }
                if (course == 5)
                {
                    for (int kk = 0; kk < 5; kk++)
                    {
                        b[j][i+kk].setBackgroundColor(Color.RED);
                        b[j][i+kk].setTextColor(Color.BLACK);
                    }
                    win(x);
                    return;
                }
                cs = course;
            }

        }
        for (int k = 1; k < 5; ++k)
        {
            if (x == 48)
            {
                if ((i - k >= 0) && (b[j][i-k].getText().equals("0")))
                {
                    course++;
                    cs1++;
                }
			else
                {
                    break;
                }
                if (course == 5)
                {
                    if (cs + cs1 == 5)
                    {
                        for (int kk = 0; kk < cs; kk++)
                        {
                            b[j][i+kk].setBackgroundColor(Color.BLUE);
                            b[j][i+kk].setTextColor(Color.BLACK);
                        }
                        for (int kk = 0; kk <= cs1; kk++)
                        {
                            b[j][i-kk].setBackgroundColor(Color.BLUE);
                            b[j][i-kk].setTextColor(Color.BLACK);
                        }
                    }
                    else
                    {
                        for (int kk = 0; kk < 5; kk++)
                        {
                            b[j][i-kk].setBackgroundColor(Color.BLUE);
                            b[j][i-kk].setTextColor(Color.BLACK);
                        }
                    }

                    win(x);
                    return;
                }
            }
            else
            {
                if ((i - k >= 0) && (b[j][i-k].getText().equals("X")))
                {
                    course++;
                    cs1++;
                }
			else
                {
                    break;
                }
                if (course == 5)
                {
                    if (cs + cs1 == 5)
                    {
                        for (int kk = 0; kk < cs; kk++)
                        {
                            b[j][i+kk].setBackgroundColor(Color.BLUE);
                            b[j][i+kk].setTextColor(Color.BLACK);
                        }
                        for (int kk = 0; kk <= cs1; kk++)
                        {
                            b[j][i-kk].setBackgroundColor(Color.RED);
                            b[j][i-kk].setTextColor(Color.BLACK);
                        }
                    }
                    else
                    {
                        for (int kk = 0; kk < 5; kk++)
                        {
                            b[j][i-kk].setBackgroundColor(Color.RED);
                            b[j][i-kk].setTextColor(Color.BLACK);
                        }
                    }
                    win(x);
                    return;
                }
            }
        }
        course = 1;
        cs1 = 0;
        //проверка 1 диагонали
        for (int k = 1; k < 5; ++k)
        {
            if (x == 48)
            {
                if ((j + k < lenW) && (i + k < lenH) && (b[j+k][i+k].getText().equals("0")))
                {
                    course++;
                }
			else
                {
                    break;
                }
                if (course == 5)
                {
                    for (int kk = 0; kk < 5; kk++)
                    {
                        b[j+kk][i+kk].setBackgroundColor(Color.BLUE);
                        b[j+kk][i+kk].setTextColor(Color.BLACK);
                    }
                    win(x);
                    return;
                }
                cs = course;
            }
            else
            {
                if ((j + k < lenW) && (i + k < lenH) && (b[j+k][i+k].getText().equals("X")))
                {
                    course++;
                }
			else
                {
                    break;
                }
                if (course == 5)
                {
                    for (int kk = 0; kk < 5; kk++)
                    {
                        b[j+kk][i+kk].setBackgroundColor(Color.RED);
                        b[j+kk][i+kk].setTextColor(Color.BLACK);
                    }
                    win(x);
                    return;
                }
                cs = course;
            }
        }
        for (int k = 1; k < 5; ++k)
        {
            if (x == 48)
            {
                if ((j - k >= 0) && (i - k >= 0) && (b[j-k][i-k].getText().equals("0")))
                {
                    course++;
                    cs1++;
                }
			else
                {
                    break;
                }
                if (course == 5)
                {
                    if (cs + cs1 == 5)
                    {
                        for (int kk = 0; kk < cs; kk++)
                        {
                            b[j+kk][i+kk].setBackgroundColor(Color.BLUE);
                            b[j+kk][i+kk].setTextColor(Color.BLACK);
                        }
                        for (int kk = 0; kk <= cs1; kk++)
                        {
                            b[j-kk][i-kk].setBackgroundColor(Color.BLUE);
                            b[j-kk][i-kk].setTextColor(Color.BLACK);
                        }
                    }
                    else
                    {
                        for (int kk = 0; kk < 5; kk++)
                        {
                            b[j-kk][i-kk].setBackgroundColor(Color.BLUE);
                            b[j-kk][i-kk].setTextColor(Color.BLACK);
                        }
                    }
                    win(x);
                    return;
                }
            }
            else
            {
                if ((j - k >= 0) && (i - k >= 0) && (b[j-k][i-k].getText().equals("X")))
                {
                    course++;
                }
			else
                {
                    break;
                }
                if (course == 5)
                {
                    if (cs + cs1 == 5)
                    {
                        for (int kk = 0; kk < cs; kk++)
                        {
                            b[j+kk][i+kk].setBackgroundColor(Color.BLUE);
                            b[j+kk][i+kk].setTextColor(Color.BLACK);
                        }
                        for (int kk = 0; kk <= cs1; kk++)
                        {
                            b[j-kk][i-kk].setBackgroundColor(Color.RED);
                            b[j-kk][i-kk].setTextColor(Color.BLACK);
                        }
                    }
                    else
                    {
                            for (int kk = 0; kk < 5; kk++)
                            {
                                b[j-kk][i-kk].setBackgroundColor(Color.RED);
                                b[j-kk][i-kk].setTextColor(Color.BLACK);
                            }
                    }
                    win(x);
                    return;
                }
            }
        }
        course = 1;
        cs1 = 0;
        //проверка 2 диагонали
        for (int k = 1; k < 5; ++k)
        {
            if (x == 48)
            {
                if ((j + k < lenW) && (i - k >= 0) && (b[j+k][i-k].getText().equals("0")))
                {
                    course++;
                }
			else
                {
                    break;
                }
                if (course == 5)
                {
                    for (int kk = 0; kk < 5; kk++)
                    {
                        b[j+kk][i-kk].setBackgroundColor(Color.BLUE);
                        b[j+kk][i-kk].setTextColor(Color.BLACK);
                    }
                    win(x);
                    return;
                }
                cs = course;
            }
            else
            {
                if ((j + k < lenW) && (i - k >= 0) && (b[j+k][i-k].getText().equals("X")))
                {
                    course++;
                }
			else
                {
                    break;
                }
                if (course == 5)
                {
                    for (int kk = 0; kk < 5; kk++)
                    {
                        b[j+kk][i-kk].setBackgroundColor(Color.RED);
                        b[j+kk][i-kk].setTextColor(Color.BLACK);
                    }
                    win(x);
                    return;
                }
                cs = course;
            }
        }
        for (int k = 1; k < 5; ++k)
        {
            if (x == 48)
            {
                if ((j - k >= 0) && (i + k < lenH) && (b[j-k][i+k].getText().equals("0")))
                {
                    course++;
                    cs1++;
                }
			else
                {
                    break;
                }
                if (course == 5)
                {
                    if (cs + cs1 == 5)
                    {
                        for (int kk = 0; kk < cs; kk++)
                        {
                            b[j+kk][i-kk].setBackgroundColor(Color.BLUE);
                            b[j+kk][i-kk].setTextColor(Color.BLACK);
                        }
                        for (int kk = 0; kk <= cs1; kk++)
                        {
                            b[j-kk][i+kk].setBackgroundColor(Color.BLUE);
                            b[j-kk][i+kk].setTextColor(Color.BLACK);
                        }
                    }
                    else
                    {
                        for (int kk = 0; kk < 5; kk++)
                        {
                            b[j-kk][i+kk].setBackgroundColor(Color.BLUE);
                            b[j-kk][i+kk].setTextColor(Color.BLACK);
                        }
                    }
                    win(x);
                    return;
                }
            }
            else
            {
                if ((j - k >= 0) && (i + k < lenH) && (b[j-k][i+k].getText().equals("X")))
                {
                    course++;
                    cs1++;
                }
			else
                {
                    break;
                }
                if (course == 5)
                {
                    if (cs + cs1 == 5)
                    {
                        for (int kk = 0; kk < cs; kk++)
                        {
                            b[j+kk][i-kk].setBackgroundColor(Color.RED);
                            b[j+kk][i-kk].setTextColor(Color.BLACK);
                        }
                        for (int kk = 0; kk <= cs1; kk++)
                        {
                            b[j-kk][i+kk].setBackgroundColor(Color.RED);
                            b[j-kk][i+kk].setTextColor(Color.BLACK);
                        }
                    }
                    else
                    {
                        for (int kk = 0; kk < 5; kk++)
                        {
                            b[j-kk][i+kk].setBackgroundColor(Color.RED);
                            b[j-kk][i+kk].setTextColor(Color.BLACK);
                        }
                    }
                    win(x);
                    return;
                }
            }
        }
    }
    void win(char x)
    {
        pobeda = 1;
       // block = 1;
        if (x == 48)
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "0 Выиграли", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (x == 88)
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "X Выиграли", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Ничья", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    void newGame() {
        for (int i = 0; i < lenH; i++) {
            for (int j = 0; j < lenW; j++) {
                if (!b[j][i].getText().equals("")) {
                    b[j][i].setText("");
                    b[j][i].setBackgroundDrawable(pi);
                }
            }
        }
        FillBoard();
        draw = lenH*lenW;
        pobeda = 0;
    }
    int PriceList(int Length, int Potential, char Who)
    {

        if (Who == 'X')
        {
            switch (Length)
            {
                case 1:
                    switch (Potential)
                    {
                        case 0:
                            return 0;
                        case 1:
                            return 1;
                        case 2:
                            return 3;
                    }

                    break;

                case 2:
                    switch (Potential)
                    {
                        case 0:
                            return 0;
                        case 1:
                            return 15;
                        case 2:
                            return 55;
                    }

                    break;
                case 3:
                    switch (Potential)
                    {
                        case 0:
                            return 0;
                        case 1:
                            return 45;
                        case 2:
                            return 550;
                    }

                    break;
                case 4:
                    switch (Potential)
                    {
                        case 0:
                            return 0;
                        case 1:
                            return 1000;
                        case 2:
                            return 1000;
                    }

                    break;
                default: return 0;
            }
        }
        else
        {
            switch (Length)
            {
                case 1:
                    switch (Potential)
                    {
                        case 0:
                            return 0;
                        case 1:
                            return 1;
                        case 2:
                            return 3;
                    }

                    break;

                case 2:
                    switch (Potential)
                    {
                        case 0:
                            return 0;
                        case 1:
                            return 10;
                        case 2:
                            return 50;
                    }

                    break;
                case 3:
                    switch (Potential)
                    {
                        case 0:
                            return 0;
                        case 1:
                            return 40;
                        case 2:
                            return 500;
                    }

                    break;
                case 4:
                    switch (Potential)
                    {
                        case 0:
                            return 0;
                        case 1:
                            return 1000;
                        case 2:
                            return 1000;
                    }

                    break;
                default: return 0;
            }
        }
        return 0;
    }
    void FillBoard()  // обнуление доски
    {
        for (int i = 0; i < lenH; i++)
        {
            for (int j = 0; j < lenW; j++)
            {
                BoardPrice[j][i] = 0;
            }
        }
    }
    void Bot() //ход бота
    {
        int i = 0, j = 0;
        int max = 0;

        for (int k = 0; k < lenW; ++k)
        {
            for (int l1 = 0; l1 < lenH; ++l1)
            {
                if (BoardPrice[k][l1] >= max)
                {
                    i = l1;
                    j = k;
                    max = BoardPrice[k][l1];
                }
            }
        }
        if (max <= 3)
        {
            int S = (int)(Math.random() * 100);
            i = 0;
            j = 0;
            while (S > 0)
            {
                if (j == lenW - 1)
                {
                    if (i == lenH - 1)
                    {
                        i = 0;
                    }
                    else
                        i++;
                    j = 0;
                }
                else j++;
                if (BoardPrice[j][i] == max)
                S--;
            }
        }
        b[jj][ii].setBackgroundDrawable(pi);
        b[j][i].setTextColor(Color.BLACK);
        b[j][i].setText("0");
        b[j][i].setBackgroundColor(Color.BLUE);
        jj = j;
        ii = i;
        BoardPrice[j][i] = 0;
        SelectCell(j, i, '0');
        CheckWin(j, i, '0');
        draw--;
        if (draw == 0)
        {
            draw();
        }

    }
    void draw()
    {
        char x = 'd';
        win(x);
    }
    void SelectCell(int j, int i, char x)
    {
        String str;
        String str1;
        char s, s1;
        if (x == 48)
        {
            str = "0";
            str1 = "X";
            s = '0';
            s1 = 'X';
        }
        else
        {
            str1 = "0";
            str = "X";
            s = 'X';
            s1 = '0';
        }

        //Проверка горизонтали
        for (int k = 1; k < 5; ++k)
        {
            if (j + k < lenW)
            {
                if (b[j + k][i].getText().equals(""))
                {
                    BoardPrice[j + k][i] = 0;
                    CheckPrice(j + k, i, s);
                    CheckPrice(j + k, i, s1);
                }
                if (b[j + k][i].getText().equals(str1))
                break;
            }
        }
        for (int k = 1; k < 5; ++k)
        {
            if (j - k >= 0)
            {
                if (b[j - k][i].getText().equals(""))
                {
                    BoardPrice[j - k][i] = 0;
                    CheckPrice(j - k, i, s);
                    CheckPrice(j - k, i, s1);
                }
                if (b[j - k][i].getText().equals(str1))
                break;
            }
        }
        //Проверка Вертикали
        for (int k = 1; k < 5; ++k)
        {
            if (i + k < lenH)
            {
                if (b[j][i + k].getText().equals(""))
                {
                    BoardPrice[j][i + k] = 0;
                    CheckPrice(j, i + k, s);
                    CheckPrice(j, i + k, s1);

                }
                if (b[j][i + k].getText().equals(str1))
                break;
            }
        }
        for (int k = 1; k < 5; ++k)
        {
            if (i - k >= 0)
            {
                if (b[j][i - k].getText().equals(""))
                {
                    BoardPrice[j][i - k] = 0;
                    CheckPrice(j, i - k, s);
                    CheckPrice(j, i - k, s1);

                }
                if (b[j][i - k].getText().equals(str1))
                break;
            }
        }
        //ПРоверка диагонали 1
        for (int k = 1; k < 5; ++k)
        {
            if ((j + k < lenW) && (i + k < lenH))
            {
                if (b[j + k][i + k].getText().equals(""))
                {
                    BoardPrice[j + k][i + k] = 0;
                    CheckPrice(j + k, i + k, s);
                    CheckPrice(j + k, i + k, s1);

                }
                if (b[j + k][i + k].getText().equals(str1))
                break;
            }
        }
        for (int k = 1; k < 5; ++k)
        {
            if ((j - k >= 0) && (i - k >= 0))
            {
                if (b[j - k][i - k].getText().equals(""))
                {
                    BoardPrice[j - k][i - k] = 0;
                    CheckPrice(j - k, i - k, s);
                    CheckPrice(j - k, i - k, s1);
                }
                if (b[j - k][i - k].getText().equals(str1))
                break;
            }
        }
        //Проверка диагонали 2
        for (int k = 1; k < 5; ++k)
        {
            if ((j + k < lenW) && (i - k >= 0))
            {
                if (b[j + k][i - k].getText().equals(""))
                {
                    BoardPrice[j + k][i - k] = 0;
                    CheckPrice(j + k, i - k, s);
                    CheckPrice(j + k, i - k, s1);
                }
                if (b[j + k][i - k].getText().equals(str1))
                break;
            }
        }
        for (int k = 1; k < 5; ++k)
        {
            if ((j - k >= 0) && (i + k < lenH))
            {
                if (b[j - k][i + k].getText().equals(""))
                {
                    BoardPrice[j - k][i + k] = 0;
                    CheckPrice(j - k, i + k, s);
                    CheckPrice(j - k, i + k, s1);
                }
                if (b[j - k][i + k].getText().equals(str1))
                break;
            }
        }
    }
    void CheckPrice(int j, int i, char x)
    {

        int Space = 1;
        int Length1 = 0;
        int Length2 = 0;
        int Attack1 = 1;
        int Attack2 = 1;
        String str;
        String str1;

        if (x == 48)
        {
            str = "0";
            str1 = "X";

        }
        else
        {
            str1 = "0";
            str = "X";

        }

        //Проверка горизонтали
        for (int k = 1; k < 5; ++k)
        {
            if (j + k < lenW)
            {
                if (b[j + k][i].getText().equals(str))    // вот для этого
                {
                    Length1++;
                }
                if (b[j + k][i].getText().equals(""))
                {
                    if (Length1 == 0) Space++;
                    else
                    {
                        if (b[j + k][i].getText().equals(""))
                        Attack1++;
                        break;
                    }
                }
                if (b[j + k][i].getText().equals(str1))
                {
                    break;
                }//выдление + игра выделение после победы
            }
            else break;
        }
        for (int k = 1; k < 5; ++k)
        {
            if (j - k >= 0)
            {
                if (b[j - k][i].getText().equals(str))
                {
                    Length2++;
                }
                if (b[j - k][i].getText().equals(""))
                {
                    if (Length2 == 0) Space++;
                    else
                    {
                        if (b[j - k][i].getText().equals(""))
                        Attack2++;
                        break;
                    }
                }
                if (b[j - k][i].getText().equals(str1))
                {
                    break;
                }
            }
            else break;
        }


        if (regimIgri == 1)
        {
            BoardPrice[j][i] += PriceList(Length1, Attack1, x);
            BoardPrice[j][i] += PriceList(Length2, Attack2, x);
        }
        else if (Space < 3)
        {
            BoardPrice[j][i] += PriceList(MAXLENGTH(Length1, Length2), MIN(Attack1, Attack2), x);
        }
        else
        {
            BoardPrice[j][i] += PriceList(Length1, Attack1, x);
            BoardPrice[j][i] += PriceList(Length2, Attack2, x);
        }
        Length1 = 0;
        Length2 = 0;
        Attack1 = 1;
        Attack2 = 1;
        Space = 1;

        //Проверка вертикали
        for (int k = 1; k < 5; ++k)
        {
            if (i + k < lenH)
            {
                if (b[j][i + k].getText().equals(str))
                {
                    Length1++;
                }
                if (b[j][i + k].getText().equals(""))
                {
                    if (Length1 == 0) Space++;
                    else
                    {
                        if (b[j][i + k].getText().equals(""))
                        Attack1++;
                        break;
                    }
                }
                if (b[j][i + k].getText().equals(str1))
                {
                    break;
                }
            }
            else break;
        }
        for (int k = 1; k < 5; ++k)
        {
            if (i - k >= 0)
            {
                if (b[j][i - k].getText().equals(str))
                {
                    Length2++;
                }
                if (b[j][i - k].getText().equals(""))
                {
                    if (Length2 == 0) Space++;
                    else
                    {
                        if (b[j][i - k].getText().equals(""))
                        Attack2++;
                        break;
                    }
                }
                if (b[j][i - k].getText().equals(str1))
                {
                    break;
                }
            }
            else break;
        }
        if (regimIgri == 1)
        {
            BoardPrice[j][i] += PriceList(Length1, Attack1, x);
            BoardPrice[j][i] += PriceList(Length2, Attack2, x);
        }
        else if (Space < 3)
        {
            BoardPrice[j][i] += PriceList(MAXLENGTH(Length1, Length2), MIN(Attack1, Attack2), x);
        }

        else
        {
            BoardPrice[j][i] += PriceList(Length1, Attack1, x);
            BoardPrice[j][i] += PriceList(Length2, Attack2, x);
        }
        Length1 = 0;
        Length2 = 0;
        Attack1 = 1;
        Attack2 = 1;
        Space = 1;


        //Проверка Диагонали 1

        for (int k = 1; k < 5; ++k)
        {
            if ((j + k < lenW) && (i + k < lenH))
            {
                if (b[j + k][i + k].getText().equals(str))
                {
                    Length1++;
                }
                if (b[j + k][i + k].getText().equals(""))
                {
                    if (Length1 == 0) Space++;
                    else
                    {
                        if (b[j + k][i + k].getText().equals(""))
                        Attack1++;
                        break;
                    }
                }
                if (b[j + k][i + k].getText().equals(str1))
                {
                    break;
                }
            }
            else break;
        }
        for (int k = 1; k < 5; ++k)
        {
            if ((j - k >= 0) && (i - k >= 0))
            {
                if (b[j - k][i - k].getText().equals(str))
                {
                    Length2++;
                }
                if (b[j - k][i - k].getText().equals(""))
                {
                    if (Length2 == 0) Space++;
                    else
                    {
                        if (b[j - k][i - k].getText().equals(""))
                        Attack2++;
                        break;
                    }
                }
                if (b[j - k][i - k].getText().equals(str1))
                {
                    break;
                }
            }
            else break;
        }
        if (regimIgri == 1)
        {
            BoardPrice[j][i] += PriceList(Length1, Attack1, x);
            BoardPrice[j][i] += PriceList(Length2, Attack2, x);
        }
        else if (Space < 3)
        {
            BoardPrice[j][i] += PriceList(MAXLENGTH(Length1, Length2), MIN(Attack1, Attack2), x);
        }
        else
        {
            BoardPrice[j][i] += PriceList(Length1, Attack1, x);
            BoardPrice[j][i] += PriceList(Length2, Attack2, x);
        }
        Length1 = 0;
        Length2 = 0;
        Attack1 = 1;
        Attack2 = 1;
        Space = 1;

        //Проверка Диагонали 2
        for (int k = 1; k < 5; ++k)
        {
            if ((j + k < lenW) && (i - k >= 0))
            {
                if (b[j + k][i - k].getText().equals(str))
                {
                    Length1++;
                }
                if (b[j + k][i - k].getText().equals(""))
                {
                    if (Length1 == 0) Space++;
                    else
                    {
                        if (b[j + k][i - k].getText().equals(""))
                        Attack1++;
                        break;
                    }
                }
                if (b[j + k][i - k].getText().equals(str1))
                {
                    break;
                }
            }
            else break;
        }
        for (int k = 1; k < 5; ++k)
        {
            if ((j - k >= 0) && (i + k < lenH))
            {
                if (b[j - k][i + k].getText().equals(str))
                {
                    Length2++;
                }
                if (b[j - k][i + k].getText().equals(""))
                {
                    if (Length2 == 0) Space++;
                    else
                    {
                        if (b[j - k][i + k].getText().equals(""))
                        Attack2++;
                        break;
                    }
                }
                if (b[j - k][i + k].getText().equals(str1))
                {
                    break;
                }
            }
            else break;
        }
        if (regimIgri == 1)
        {
            BoardPrice[j][i] += PriceList(Length1, Attack1, x);
            BoardPrice[j][i] += PriceList(Length2, Attack2, x);  // реализация сложности
        }
        else if (Space < 3)
        {
            BoardPrice[j][i] += PriceList(MAXLENGTH(Length1, Length2), MIN(Attack1, Attack2), x);
        }
        else
        {
            BoardPrice[j][i] += PriceList(Length1, Attack1, x);
            BoardPrice[j][i] += PriceList(Length2, Attack2, x);
        }

    }
    int MIN(int a, int b)
    {
        if(a>b)
            return b;
        else
            return a;
    }
    int MAXLENGTH(int a, int b)
    {
        if(a+b > 4)
            return  4;
        else
            return a+b;
    }
    void Player(int id) //ход игрока кароче
    {

        int j = id % lenW;
        int i = id / lenW;
        b[j][i].setTextColor(Color.BLACK);
        b[j][i].setText("X");
        BoardPrice[j][i] = 0;
        SelectCell(j, i, 'X');
        CheckWin(j, i, 'X');
        draw--;
        if (draw == 0)
        {
            draw();
        }
    }
}