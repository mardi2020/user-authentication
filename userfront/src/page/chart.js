import * as React from 'react';
import { Table, TableBody, TableCell, TableHead, TableRow } from '@mui/material/';
// Generate Order Data

export default function Charts({ users }) {
    return (
        <React.Fragment>
            <Table size="small">
                <TableHead>
                    <TableRow>
                        <TableCell name='userId'>userId</TableCell>
                        <TableCell name='email'>이메일</TableCell>
                        <TableCell name='name'>이름</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {users.map((row) => (
                        <TableRow key={row.userId}>
                            <TableCell>{row.userId}</TableCell>
                            <TableCell>{row.email}</TableCell>
                            <TableCell>{row.name}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </React.Fragment>
    );
}