package org.manjot.commands.misc;

import org.manjot.Error;
import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CalculateCommand extends Command implements CommandListener {
    public CalculateCommand() {
        this.setName("calculate")
                .setAliases("calc", "math", "maths", "solve", "calculator")
                .setDescription("Solve a mathematical expression")
                .setUsage("calculate <expression>")
                .setType(CommandType.MISCELLANEOUS);
    }

    public static double calculate(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ')
                    nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length())
                    throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            // | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (eat('+'))
                        x += parseTerm(); // addition
                    else if (eat('-'))
                        x -= parseTerm(); // subtraction
                    else
                        return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (eat('*'))
                        x *= parseFactor(); // multiplication
                    else if (eat('/'))
                        x /= parseFactor(); // division
                    else
                        return x;
                }
            }

            double parseFactor() {
                if (eat('+'))
                    return parseFactor(); // unary plus
                if (eat('-'))
                    return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.')
                        nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z') { // functions
                    while (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z')
                        nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equalsIgnoreCase("sqrt"))
                        x = Math.sqrt(x);
                    else if (func.equalsIgnoreCase("sin"))
                        x = Math.sin(x);
                    else if (func.equalsIgnoreCase("cos"))
                        x = Math.cos(x);
                    else if (func.equalsIgnoreCase("tan"))
                        x = Math.tan(x);
                    else if (func.equalsIgnoreCase("cosec"))
                        x = 1 / Math.sin(x);
                    else if (func.equalsIgnoreCase("sec"))
                        x = 1 / Math.cos(x);
                    else if (func.equalsIgnoreCase("cot"))
                        x = 1 / Math.tan(x);
                    else if (func.equalsIgnoreCase("arcsin"))
                        x = Math.asin(x);
                    else if (func.equalsIgnoreCase("arccos"))
                        x = Math.acos(x);
                    else if (func.equalsIgnoreCase("arctan"))
                        x = Math.atan(x);
                    else if (func.equalsIgnoreCase("sinh"))
                        x = Math.sinh(x);
                    else if (func.equalsIgnoreCase("cosh"))
                        x = Math.cosh(x);
                    else if (func.equalsIgnoreCase("tanh"))
                        x = Math.tanh(x);
                    else if (func.equalsIgnoreCase("abs"))
                        x = Math.abs(x);
                    else if (func.equalsIgnoreCase("signum"))
                        x = Math.signum(x);
                    else if (func.equalsIgnoreCase("log"))
                        x = Math.log10(x);
                    else if (func.equalsIgnoreCase("ln"))
                        x = Math.log1p(x - 1);
                    else
                        throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^'))
                    x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        if (args.length == 0) {
            replyInvalidUsage(message);
            return;
        }
        try {
            message.reply(Utils.formatDouble(calculate(Utils.messageFrom(args, 0)))).queue();
        } catch (Exception e) {
            Error.replyException(message, e);
        }
    }
}
