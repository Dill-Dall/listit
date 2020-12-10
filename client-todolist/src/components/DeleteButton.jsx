import React, { Component } from 'react';
import axios from 'axios';

export default class DeleteButton extends Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async handleSubmit(event) {

        let result = await axios({
            method: 'delete',
            url: '/todolist/item?',
            params: {
                ItemId: this.props.itemId
            }});

        event.preventDefault();
    }

    render() {
        return (
            <form  onSubmit={this.handleSubmit}>
            <button className="btn btn-danger" type="submit " >Delete</button>
            </form>
        );
    };
}