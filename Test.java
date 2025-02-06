import fitz  # PyMuPDF
import pdfplumber
import pandas as pd
import difflib

def extract_text_from_pdf(pdf_path):
    """
    Extracts non-table text from a PDF file.
    
    Args:
        pdf_path (str): Path to the PDF file.
    
    Returns:
        dict: A dictionary where keys are page numbers (starting from 1) and values are the extracted text of each page.
    """
    doc = fitz.open(pdf_path)
    text_by_page = {}
    for page_num in range(len(doc)):
        page = doc.load_page(page_num)
        text = page.get_text("text")  # Extract text
        text_by_page[page_num + 1] = text
    return text_by_page

def extract_tables_from_pdf(pdf_path):
    """
    Extracts tables from a PDF file using pdfplumber.
    
    Args:
        pdf_path (str): Path to the PDF file.
    
    Returns:
        dict: A dictionary where keys are page numbers and values are lists of dataframes representing tables.
    """
    tables_by_page = {}
    with pdfplumber.open(pdf_path) as pdf:
        for page_num, page in enumerate(pdf.pages, start=1):
            tables = page.extract_tables()
            if tables:
                tables_by_page[page_num] = [pd.DataFrame(table) for table in tables]
    return tables_by_page

def compare_texts(text1, text2):
    """
    Compares two texts and returns differences.

    Args:
        text1 (str): First text.
        text2 (str): Second text.

    Returns:
        list: A list of differences.
    """
    diff = difflib.unified_diff(
        text1.splitlines(keepends=True),
        text2.splitlines(keepends=True),
        lineterm=''
    )
    return list(diff)

def compare_tables(tables1, tables2):
    """
    Compares tables extracted from two PDFs.

    Args:
        tables1 (dict): Tables from the first PDF.
        tables2 (dict): Tables from the second PDF.

    Returns:
        dict: Dictionary with page numbers as keys and mismatched tables as values.
    """
    differences = {}
    all_pages = set(tables1.keys()).union(set(tables2.keys()))
    
    for page in all_pages:
        t1 = tables1.get(page, [])
        t2 = tables2.get(page, [])
        mismatched_tables = []
        
        for i, (table1, table2) in enumerate(zip(t1, t2)):
            if not table1.equals(table2):
                mismatched_tables.append((i, table1, table2))  # Store table index and mismatched tables
        
        if mismatched_tables:
            differences[page] = mismatched_tables
    
    return differences

def highlight_differences(pdf_path, differences, output_path):
    """
    Highlights differences in the PDF and saves it.

    Args:
        pdf_path (str): Original PDF.
        differences (dict): Differences detected.
        output_path (str): Path to save the highlighted PDF.
    """
    doc = fitz.open(pdf_path)
    for page_num, diffs in differences.items():
        page = doc.load_page(page_num - 1)
        for diff in diffs:
            text_instances = page.search_for(diff)
            for inst in text_instances:
                highlight = page.add_highlight_annot(inst)
                highlight.update()
    doc.save(output_path)

def compare_pdfs(pdf_path1, pdf_path2, output_path1, output_path2):
    """
    Compares two PDFs, including both text and tables, and highlights differences.

    Args:
        pdf_path1 (str): Path to the first PDF file.
        pdf_path2 (str): Path to the second PDF file.
        output_path1 (str): Path to save the first PDF with highlighted differences.
        output_path2 (str): Path to save the second PDF with highlighted differences.
    """
    # Extract text
    text1_by_page = extract_text_from_pdf(pdf_path1)
    text2_by_page = extract_text_from_pdf(pdf_path2)

    # Extract tables
    tables1_by_page = extract_tables_from_pdf(pdf_path1)
    tables2_by_page = extract_tables_from_pdf(pdf_path2)

    # Compare text differences
    text_differences1 = {}
    text_differences2 = {}

    all_pages = set(text1_by_page.keys()).union(set(text2_by_page.keys()))
    for page_num in all_pages:
        text1 = text1_by_page.get(page_num, "")
        text2 = text2_by_page.get(page_num, "")
        diffs = compare_texts(text1, text2)
        if diffs:
            text_differences1[page_num] = [line[2:] for line in diffs if line.startswith('- ')]
            text_differences2[page_num] = [line[2:] for line in diffs if line.startswith('+ ')]

    # Compare table differences
    table_differences = compare_tables(tables1_by_page, tables2_by_page)

    # Print table differences
    for page_num, mismatches in table_differences.items():
        print(f"Page {page_num} has table differences:")
        for table_index, table1, table2 in mismatches:
            print(f"Table {table_index} mismatch:")
            print("Table 1:\n", table1)
            print("Table 2:\n", table2)
    
    # Highlight text differences
    highlight_differences(pdf_path1, text_differences1, output_path1)
    highlight_differences(pdf_path2, text_differences2, output_path2)

# Example usage
pdf1 = 'path/to/first.pdf'
pdf2 = 'path/to/second.pdf'
output1 = 'path/to/first_highlighted.pdf'
output2 = 'path/to/second_highlighted.pdf'

compare_pdfs(pdf1, pdf2, output1, output2)
