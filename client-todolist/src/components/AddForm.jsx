import React, { Component } from 'react';
import axios from 'axios';

export default class AddForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            textField: '',
            chosencategories: [],
            categories: ['WORK', 'SHOPPING', 'ADMIN', 'OTHER','NON'],
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async handleSubmit(event) {
        alert(this.state.textField)
        let result = await axios({
            method: 'post',     //put
            url: '/todolist/item?',
            data: {
                textField: this.state.textField,
                listId: this.props.listid,
                category: this.state.chosencategories,
            }
          });

        alert(result)
        this.setState({ textfield: '' });
        this.setState({ textfchosencategoriesield: [] });
        event.preventDefault();
    }

    handleChange(event) {
        this.setState({ textField: event.target.value });
    }

    handleSelectChange = (e) => {
        let options = e.target.options;
        let selectedOptions = [];

        for (let i = 0; i < options.length; i++) {
            if (options[i].selected) {
                selectedOptions.push(options[i].value);
            }
        }

        this.setState({ chosencategories: selectedOptions });
    }

    render() {
        return (
            <form className="form-inline jumbotron" ref="form" onSubmit={this.handleSubmit}>
                <label>Text:</label>
                <textarea value={this.state.textfield} onChange={this.handleChange} />
                <label>Category:</label>
                <select multiple={true} value={this.state.vachosencategorieslues} onChange={this.handleSelectChange}>
                    {this.state.categories.map((item, index) =>
                        <option value={index} key={index}>{item}</option>
                    )}
                </select>

                <button className="btn btn-primary" type="submit" >Add</button>
            </form>

        );
    }
}