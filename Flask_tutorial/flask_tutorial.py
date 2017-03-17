# https://www.youtube.com/watch?v=ZVGwqnjOKjk&index=1&list=PL6gx4Cwl9DGDi9F_slcQK7knjtO8TUvUs
from flask import Flask, request, render_template

app = Flask(__name__)

# Tying the URL with the function
@app.route("/")
@app.route("/<user>")
def index(user=None):
    #return "Method used : %s" % request.method
    return render_template("user.html", user = user)

@app.route("/shopping")
def shopping():
    food = ["Cheese", "Tuna", "Beef"]
    return render_template("shopping.html", food = food)

@app.route("/profilepage/<name>")
def profilepage(name):
    return render_template("profilepage.html", name = name)

@app.route("/bacon", methods=['GET', 'POST'])
def bacon():
    if request.method == 'POST':
        return "You are using POST"
    else:
        return "You are using GET"

@app.route("/tuna")
def tuna():
    return '<h2>Tuna is good</h2>' # Never put html directly in the HTML (use tempalate)

@app.route('/profile/<username>')
def profile(username):
    return "<h2>Hey there %s</h2>" % username

@app.route('/post/<int:post_id>')
def show_post(post_id):
    return "<h2>Post Id is %s</h2>" % post_id

# Only run if you are on main page
if __name__ == "__main__":
    app.run(debug=True)