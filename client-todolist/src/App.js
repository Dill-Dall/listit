import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import axios from 'axios';
import { Button } from 'bootstrap';
import { Form } from 'redux-form';
import AddForm from './components/AddForm'
import DeleteButton from './components/DeleteButton'

class App extends Component {

  constructor(props) {
    super(props);
    this.state = {
      categories: ['WORK', 'SHOPPING', 'ADMIN', 'OTHER', 'NON', '-'],
      chosencatagory: '-',

      todolist: {
        id: '',
        title: '',
        listItems: [],
      },
      filteredlist: []

    }
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  async handleSubmit(event) {
    alert(this.state.textField)
    let result = await axios({
      method: 'delete',
      url: '/todolist/item?',
      data: {
        itemId: this.state.textField
      }
    });

    this.setState({ textfield: '' });
    this.setState({ textfchosencategoriesield: [] });
    event.preventDefault();
  }

  async handleChange(event) {
    await this.setState({ chosencatagory: event.target.value });
    console.log(this.state.todolist.listItems.filter(it => it.categories.includes("ADMIN")))
    if (this.state.chosencatagory == '-') {
      this.setState({ filteredlist: this.state.todolist.listItems });
    } else {
      this.setState({ filteredlist: this.state.todolist.listItems.filter(it => it.categories.includes(this.state.chosencatagory)) });
    }
    console.log(this.state.chosencatagory)
  }

  async componentDidMount() {
    let result = await axios.get('/todolist?Listid=1')
    console.log(result);
    this.setState({ todolist: result.data })
    this.setState({ filteredlist: result.data.listItems })
  }

  render() {
    return (
      <div className="container">
        <h1>#{this.state.todolist.id} {this.state.todolist.title}</h1>
        <div className="header">
          <p>Title</p>
          <p>Categories</p>
        </div>
        {this.state.todolist.listItems.length > 0 ? (<div>{this.state.filteredlist.map((todo) =>
          <ul className="list-group">
            <li
              key={todo.id}
              className="list-group-item d-flex justify-content-between align-items-center"
            >
              <tr>{todo.id}  {todo.textField}</tr>  <tr><span className="categories">{todo.categories.join('   :   ')}</span></tr>


              <DeleteButton itemId={todo.id} />
            </li>
          </ul>
        )
        }</div>
        ) : (
            <div className="spinner-border text-primary" role="status">
              <span className="visually-hidden" />
            </div>
          )}
        <AddForm listid={this.state.todolist.id} />
      </div>
    );
  }
}

export default App;